package com.kubekthecreator.projects.youtubeclone.repository;

import com.kubekthecreator.projects.youtubeclone.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {
}
