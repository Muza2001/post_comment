package com.example.springbootproject.repositories;

import com.example.springbootproject.entities.Community;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<List<Post>> findAllByCommunity(Community community);

    Optional<List<Post>> findAllByOwner(User owner);
}
