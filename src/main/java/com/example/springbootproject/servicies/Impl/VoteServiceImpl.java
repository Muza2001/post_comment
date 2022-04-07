package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.VoteRequest;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.Vote;
import com.example.springbootproject.enums.VoteType;
import com.example.springbootproject.excptions.CommentNotFoundException;
import com.example.springbootproject.excptions.PostNotFoundException;
import com.example.springbootproject.repositories.PostRepository;
import com.example.springbootproject.repositories.VoteRepository;
import com.example.springbootproject.servicies.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final MyUserService myUserService;

    @Override
    public void vote(VoteRequest request) {

        Post post = postRepository.findById(request.getPostId()).orElseThrow(()
                -> new PostNotFoundException("Post id not found"));

        Optional<Vote> voteOptional = voteRepository.findTopByPostAndOwnerOrderByIdDesc
                (post, myUserService.getCurrentUser());
        if (voteOptional.isPresent() && voteOptional.get().getVoteType().equals(request.getType()))
            throw new CommentNotFoundException("Siz ushbu postga 2chi marta ovoz beryapsiz");

        if (request.getType().equals(VoteType.LIKE)){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(map(request,post));
        postRepository.save(post);

    }

    private Vote map(VoteRequest request, Post post) {
        return Vote.builder()
                .voteType(request.getType())
                .owner(myUserService.getCurrentUser())
                .post(post)
                .build();
    }
}
