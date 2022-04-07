package com.example.springbootproject.repositories;

import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import com.example.springbootproject.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndOwnerOrderByIdDesc(Post post, User owner);


}
