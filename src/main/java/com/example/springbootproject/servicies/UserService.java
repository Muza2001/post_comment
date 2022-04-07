package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.response.AuthenticationResponse;
import com.example.springbootproject.dtos.request.LoginRequest;
import com.example.springbootproject.dtos.request.RefreshTokenRequest;
import com.example.springbootproject.dtos.request.RegisterRequest;
import com.example.springbootproject.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserService {

    ResponseEntity<?> register(RegisterRequest dto);

    ResponseEntity<?> login(LoginRequest request);

    void verification(String token);

    ResponseEntity<AuthenticationResponse> refreshToken(RefreshTokenRequest dto) ;

    ResponseEntity<String> logout(RefreshTokenRequest request);

    User getCurrentUser();
}
