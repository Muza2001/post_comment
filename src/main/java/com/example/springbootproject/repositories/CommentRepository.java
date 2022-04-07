package com.example.springbootproject.repositories;

import com.example.springbootproject.entities.Comment;
import com.example.springbootproject.entities.Post;
import com.example.springbootproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findAllByPost(Post post);

    Optional<List<Comment>> findAllByOwner(User owner);
}
