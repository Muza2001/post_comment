package com.example.springbootproject.servicies;

import com.example.springbootproject.dtos.request.VoteRequest;

public interface VoteService {
    void vote(VoteRequest request);
}
