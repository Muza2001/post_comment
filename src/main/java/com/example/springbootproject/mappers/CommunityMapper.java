package com.example.springbootproject.mappers;

import com.example.springbootproject.dtos.request.CommunityRequest;
import com.example.springbootproject.entities.Community;
import com.example.springbootproject.entities.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CommunityMapper {

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Community fromDto(CommunityRequest request);

    @Mapping(target = "countOfPosts", expression = "java(mapPosts(community.getPosts()))")
    CommunityRequest toDto(Community community);

    default Long mapPosts(Set<Post> posts) {
        return Long.parseLong(String.valueOf(posts.size()));
    }
}
