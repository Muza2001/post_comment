package com.example.springbootproject.controllers;

import com.example.springbootproject.dtos.request.CommentRequest;
import com.example.springbootproject.servicies.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CommentRequest request){
        commentService.save(request);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @GetMapping("/by_post/{id}")
    public ResponseEntity<List<CommentRequest>> findAllById(@PathVariable("id") Long id){
        return ResponseEntity.status(200).body(commentService.findAllById(id));
    }

    @GetMapping("by_username/{username}")
    public ResponseEntity<List<CommentRequest>> findAllByOwner(@PathVariable("username") String username){
        return ResponseEntity.status(200).body(commentService.findAllByOwner(username));
    }
}
