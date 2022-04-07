package com.example.springbootproject.mappers;

import com.example.springbootproject.dtos.request.CommentRequest;
import com.example.springbootproject.entities.Comment;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "text", source = "request.text")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "owner", source = "user")
    Comment toEntity(CommentRequest request, Post post, User user);

    @Mapping(target = "username", expression = "java(entity.getOwner().getUsername())")
    @Mapping(target = "postId", expression = "java(entity.getPost().getId())")
    CommentRequest fromEntity(Comment entity);

}
