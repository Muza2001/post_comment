package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.PostRequest;
import com.example.springbootproject.dtos.response.PostResponse;

import java.util.List;

public interface PostService {
    PostRequest create(PostRequest request);

    List<PostResponse> findAll();

    PostResponse findById(Long id);

    List<PostResponse> findAllByCommunity(Long communityId);

    List<PostResponse> findAllByUsername(String username);
}
