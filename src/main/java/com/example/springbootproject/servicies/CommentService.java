package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.CommentRequest;

import java.util.List;

public interface CommentService {
    void save(CommentRequest request);

    List<CommentRequest> findAllById(Long id);

    List<CommentRequest> findAllByOwner(String username);
}

