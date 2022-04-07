package com.example.springbootproject.repositories;

import com.example.springbootproject.entities.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {

    Optional<Community> findByName(@NotNull(message = "The name of the community should not be empty") String name);

}
