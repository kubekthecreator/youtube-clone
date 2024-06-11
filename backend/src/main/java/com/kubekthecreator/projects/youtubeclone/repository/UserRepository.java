package com.kubekthecreator.projects.youtubeclone.repository;

import com.kubekthecreator.projects.youtubeclone.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findBySub(Object sub);
}
