package com.example.springbootproject.controllers;

import com.example.springbootproject.dtos.request.PostRequest;
import com.example.springbootproject.dtos.response.PostResponse;
import com.example.springbootproject.servicies.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostRequest> create(@Valid @RequestBody PostRequest request ){
        return ResponseEntity.status(201).body(postService.create(request));
    }

    @GetMapping("/find_all")
    public ResponseEntity<List<PostResponse>> findAll(){
        return ResponseEntity.status(200).body(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(postService.findById(id));
    }

    @GetMapping("/by_community/{id}")
    public ResponseEntity<List<PostResponse>> findAllByCommunity(@PathVariable("id") Long communityId){
        return ResponseEntity.status(200).body(postService.findAllByCommunity(communityId));
    }

    @GetMapping("/by_username/{username}")
    public ResponseEntity<List<PostResponse>> findByUsername(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAllByUsername(username));
    }

}
