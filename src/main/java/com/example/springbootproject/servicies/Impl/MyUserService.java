package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.LoginRequest;
import com.example.springbootproject.dtos.request.NotificationEmail;
import com.example.springbootproject.dtos.request.RefreshTokenRequest;
import com.example.springbootproject.dtos.request.RegisterRequest;
import com.example.springbootproject.dtos.response.AuthenticationResponse;
import com.example.springbootproject.entities.User;
import com.example.springbootproject.entities.auth.VerificationToken;
import com.example.springbootproject.repositories.VerificationTokenRepository;
import com.example.springbootproject.repositories.UserRepository;
import com.example.springbootproject.security.JwtProvider;
import com.example.springbootproject.servicies.MailSerivec;
import com.example.springbootproject.servicies.RefreshTokenService;
import com.example.springbootproject.servicies.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MyUserService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final MailSerivec mailService;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new IllegalArgumentException("Email not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getIsEnabled(),
                true,
                true,
                true,
                grantedAuthority("USER")
        );
    }

    private Collection<? extends GrantedAuthority> grantedAuthority(String user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user));
    }

    public ResponseEntity<?> register(RegisterRequest userDto) {
       User user = new User();
       user.setEmail(userDto.getEmail());
       user.setUsername(userDto.getUsername());
       user.setExpiration(Instant.now());
       user.setFull_name(user.getFull_name());
       user.setPassword(passwordEncoder.encode(userDto.getPassword()));
       user.setIsEnabled(false);
       userRepository.save(user);
       String token = generateTokenForVerification(user);

        mailService.send(new NotificationEmail("Accountingizni activatsia qiling",
                    user.getEmail(), "<h1> Ushbu link orqali </h1>" +
                "http://loaclhost:8081/api/v1/auth/verification/" + token));
        return ResponseEntity.status(201).body(token);
    }

    private String generateTokenForVerification(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationData(Instant.now().plus(1, ChronoUnit.HOURS));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Value("${general.expiration.time}")
    Long EXPIRATION_TIME;
    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String generateToken = jwtProvider.generateToken((org.springframework.security.core.userdetails.User) authenticate.getPrincipal());
        // TODO: 2/25/22 check if user login details match, if not handle it.

        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticationToken(generateToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getRefreshToken())
                .username(request.getUsername())
                .expirationData(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public void verification(String token) {
        Optional<VerificationToken> refreshToken = verificationTokenRepository.findByToken(token);
        if (refreshToken.isPresent()){
            VerificationToken verificationToken = refreshToken.get();
            if (verificationToken.getExpirationData().isBefore(Instant.now())){
                log.error("Token expiration Data {}", verificationToken.getExpirationData());
                throw new RuntimeException("Token expiration");
            }
            User user = verificationToken.getUser();
            user.setIsEnabled(true);
            userRepository.save(user);
        }
    }

    public ResponseEntity<AuthenticationResponse> refreshToken(RefreshTokenRequest request) {
        refreshTokenService.validationToken(request.getRefreshToken());
        String authenticationToken = jwtProvider.generateTokenWithUsername(request.getUsername());
        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticationToken(authenticationToken)
                .refreshToken(request.getRefreshToken())
                .username(request.getUsername())
                .expirationData(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        return ResponseEntity.ok(response);

    }

    public ResponseEntity<String> logout(RefreshTokenRequest request) {
        refreshTokenService.refreshTokenDelete(request);
        return ResponseEntity.status(200).body("Successfully logged auth");
    }

    @Override
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject()).orElseThrow(()
                -> new UsernameNotFoundException("username Not found"));
    }

    public boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
