package com.example.springbootproject.enums;

public enum VoteType {

    LIKE(1),
    DISLIKE(-1);

    private Integer vote;

    VoteType(Integer vote) {
        this.vote = vote;
    }

    public Integer getVote() {
        return vote;
    }
}
