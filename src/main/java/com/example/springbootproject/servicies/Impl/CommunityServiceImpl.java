package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.CommunityRequest;
import com.example.springbootproject.entities.Community;
import com.example.springbootproject.excptions.CommunityNotFoundException;
import com.example.springbootproject.repositories.CommunityRepository;
import com.example.springbootproject.servicies.CommunityService;
import com.example.springbootproject.mappers.CommunityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityMapper mapper;

    @Override
    public CommunityRequest create(CommunityRequest request) {
        Community savedCommunity = communityRepository.save(mapper.fromDto(request));
        request.setId(savedCommunity.getId());
        return request;
    }

    @Override
    public List<CommunityRequest> findAll() {
        return communityRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommunityRequest findById(Long id) {
        Community community =
                communityRepository
                        .findById(id).orElseThrow(() ->
                        new CommunityNotFoundException("Id not found"));
        return mapper.toDto(community);
    }
}
