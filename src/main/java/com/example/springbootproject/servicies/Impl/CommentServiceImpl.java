package com.example.springbootproject.servicies.Impl;

import com.example.springbootproject.dtos.request.CommentRequest;
import com.example.springbootproject.dtos.request.NotificationEmail;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import com.example.springbootproject.excptions.CommentNotFoundException;
import com.example.springbootproject.excptions.PostNotFoundException;
import com.example.springbootproject.mappers.CommentMapper;
import com.example.springbootproject.repositories.CommentRepository;
import com.example.springbootproject.repositories.PostRepository;
import com.example.springbootproject.repositories.UserRepository;
import com.example.springbootproject.servicies.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final MyUserService myUserService;

    @Override
    public void save(CommentRequest request) {

        User user = myUserService.getCurrentUser();
        Post post = postRepository.findById(request.getPostId()).orElseThrow(()
                -> new PostNotFoundException("Post id not found by id :" + request.getPostId()));
        commentRepository.save(commentMapper.toEntity(request, post, user));
       /* mailService.send(new NotificationEmail(
                "Your post's new comment from " +
                user.getUsername() , post.getOwner().getEmail(),
                "Post name { " + post.getPostName() + " }"));*/
    }

    @Override
    public List<CommentRequest> findAllById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()
                -> new PostNotFoundException("Post id not found by id :" + id));

        return commentRepository.findAllByPost(post).orElseThrow(()
                -> new PostNotFoundException("Post not found "))
                .stream()
                .map(commentMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentRequest> findAllByOwner(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("Username not found"));
        return commentRepository.findAllByOwner(user).orElseThrow(()
                -> new CommentNotFoundException("Comment not found"))
                .stream()
                .map(commentMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
