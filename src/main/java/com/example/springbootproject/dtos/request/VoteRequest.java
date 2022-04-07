package com.example.springbootproject.dtos.request;

import com.example.springbootproject.enums.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    private VoteType type;
    private Long postId;

}
