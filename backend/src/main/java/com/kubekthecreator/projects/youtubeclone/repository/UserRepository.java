package com.kubekthecreator.projects.youtubeclone.repository;

import com.kubekthecreator.projects.youtubeclone.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
