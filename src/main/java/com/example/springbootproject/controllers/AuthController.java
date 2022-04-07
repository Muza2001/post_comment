package com.example.springbootproject.controllers;

import com.example.springbootproject.dtos.response.AuthenticationResponse;
import com.example.springbootproject.dtos.request.LoginRequest;
import com.example.springbootproject.dtos.request.RefreshTokenRequest;
import com.example.springbootproject.dtos.request.RegisterRequest;
import com.example.springbootproject.servicies.Impl.MyUserService;
import com.example.springbootproject.servicies.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> singIn(@RequestBody RegisterRequest dto){
        return userService.register(dto);
    }

    @PostMapping("/verification/{token}")
    public ResponseEntity<?> verification(@PathVariable String token){
        userService.verification(token);
        return ResponseEntity.ok().body("Account active");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request){
        return userService.logout(request);
    }

    @PostMapping("/refresh/token")
    public HttpEntity<AuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest dto) {
        return userService.refreshToken(dto);
    }


}
