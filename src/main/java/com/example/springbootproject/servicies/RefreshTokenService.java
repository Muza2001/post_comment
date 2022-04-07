package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.RefreshTokenRequest;
import com.example.springbootproject.entities.auth.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    void validationToken(String refreshToken);

    void refreshTokenDelete(RefreshTokenRequest request);
}
