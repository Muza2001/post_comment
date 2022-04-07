package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.PostRequest;
import com.example.springbootproject.dtos.response.PostResponse;
import com.example.springbootproject.entities.Community;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import com.example.springbootproject.excptions.CommunityNotFoundException;
import com.example.springbootproject.excptions.PostNotFoundException;
import com.example.springbootproject.mappers.PostMapper;
import com.example.springbootproject.repositories.CommunityRepository;
import com.example.springbootproject.repositories.PostRepository;
import com.example.springbootproject.repositories.UserRepository;
import com.example.springbootproject.servicies.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper mapper;
    private final CommunityRepository communityRepository;
    private final MyUserService myUserService;
    private final UserRepository userRepository;

    @Override
    public PostRequest create(PostRequest request) {
        Community community = communityRepository.findByName(
                request.getCommunityName()).orElseThrow(()
                -> new CommunityNotFoundException("Community name not found. By name :"
                + request.getCommunityName()));
        Post post = postRepository.save(mapper.map(request, community, myUserService.getCurrentUser()));
        request.setId(post.getId());
        return request;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> findAll() {
        return postRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    @Override
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()
                -> new PostNotFoundException("Post id not found . By Id : " + id));
        return mapper.toDto(post);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PostResponse> findAllByCommunity(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()
                -> new CommunityNotFoundException("Community id not found. By id : " + communityId));
        List<Post> posts = postRepository.findAllByCommunity(community).orElseThrow(() ->
                new PostNotFoundException("Community not found. By community : " + community.getName()));
        return posts.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> findAllByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found : " + username));
        List<Post> posts = postRepository.findAllByOwner(user).orElseThrow(()
                -> new UsernameNotFoundException("User not found : " + user.getUsername()));
        return posts.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
