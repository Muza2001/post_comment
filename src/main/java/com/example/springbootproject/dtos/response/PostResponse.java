package com.example.springbootproject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;
    private String postName;
    private String description;
    private String url;
    private String username;
    private String communityName;
    private Integer voteCount;
    private Integer commentCount;
    private boolean like;
    private boolean dislike;
}
