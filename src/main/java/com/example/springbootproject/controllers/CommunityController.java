package com.example.springbootproject.controllers;

import com.example.springbootproject.dtos.request.CommunityRequest;
import com.example.springbootproject.servicies.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/community")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/create")
    public ResponseEntity<CommunityRequest> create(@Valid @RequestBody CommunityRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(communityService.create(request));
    }

    @GetMapping("/get_community")
    public ResponseEntity<List<CommunityRequest>> findAll(){
        return ResponseEntity.status(200).body(communityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityRequest> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(communityService.findById(id));
    }
}
