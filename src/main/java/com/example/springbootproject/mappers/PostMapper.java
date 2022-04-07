package com.example.springbootproject.mappers;

import com.example.springbootproject.dtos.request.PostRequest;
import com.example.springbootproject.dtos.response.PostResponse;
import com.example.springbootproject.entities.Community;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import com.example.springbootproject.entities.Vote;
import com.example.springbootproject.enums.VoteType;
import com.example.springbootproject.excptions.CommentNotFoundException;
import com.example.springbootproject.repositories.CommentRepository;
import com.example.springbootproject.repositories.VoteRepository;
import com.example.springbootproject.servicies.Impl.MyUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MyUserService myUserService;

    @Autowired
    private CommentRepository commentRepository;

    @Mapping(target = "postName", source = "request.postName")
    @Mapping(target = "url", source = "request.url")
    @Mapping(target = "owner", source = "currentUser")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "community", source = "community")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    public abstract Post map(PostRequest request, Community community, User currentUser);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "username", source = "owner.username")
    @Mapping(target = "communityName", source = "community.name")
    @Mapping(target = "like", expression = "java(isLike(entity))")
    @Mapping(target = "dislike", expression = "java(isDislike(entity))")
    @Mapping(target = "commentCount", expression = "java(getCommentCount(entity))")
    public abstract PostResponse toDto(Post entity);

    Integer getCommentCount(Post entity){
        return commentRepository.findAllByPost(entity).orElseThrow(()
                -> new CommentNotFoundException("Post not found")).size();
    }

    boolean isLike(Post post){
        return checkVote(post, VoteType.LIKE);
    }
    boolean isDislike(Post post){
        return checkVote(post, VoteType.DISLIKE);
    }

    private boolean checkVote(Post post, VoteType like) {
        if (myUserService.isLoggedIn()) {
            Optional<Vote> optionalVote =
                    voteRepository.findTopByPostAndOwnerOrderByIdDesc(
                            post, myUserService.getCurrentUser());
            return optionalVote.filter(vote -> vote.getVoteType().equals(like)).isPresent();
        }
        return false;
    }
}
