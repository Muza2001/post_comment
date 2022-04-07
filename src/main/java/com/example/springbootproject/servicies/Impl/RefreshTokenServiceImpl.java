package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.RefreshTokenRequest;
import com.example.springbootproject.entities.auth.RefreshToken;
import com.example.springbootproject.excptions.RefreshTokenException;
import com.example.springbootproject.repositories.RefreshTokenRepository;
import com.example.springbootproject.servicies.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Instant.now());
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public void validationToken(String refreshToken) {
        refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new RefreshTokenException("Refresh token invalid"));
    }

    @Override
    public void refreshTokenDelete(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(request.
                getRefreshToken()).orElseThrow(() ->
                new RefreshTokenException("Token not found"));
        refreshTokenRepository.delete(refreshToken);
    }

}
