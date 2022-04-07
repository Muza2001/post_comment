package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.CommunityRequest;

import java.util.List;

public interface CommunityService {

    CommunityRequest create(CommunityRequest request);

    List<CommunityRequest> findAll();

    CommunityRequest findById(Long id);
}
