package com.kubekthecreator.projects.youtubeclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Document(value = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String emailAddress;
    private String sub;
    private Set<String> subscribedToUsers = ConcurrentHashMap.newKeySet();
    private Set<String> subscribes = ConcurrentHashMap.newKeySet();
    private Set<String> videoHistory = ConcurrentHashMap.newKeySet();
    private Set<String> likedVideos = ConcurrentHashMap.newKeySet();
    private Set<String> dislikedVideos = ConcurrentHashMap.newKeySet();

    public void addToLikedVideos(String videoId) {
        likedVideos.add(videoId);
    }

    public void removeFromLikedVideos(String videoId) {
        likedVideos.remove(videoId);
    }

    public void removeFromDislikedVideos(String videoId) {
        dislikedVideos.remove(videoId);
    }

    public void addToDislikedVideos(String videoId) {
        dislikedVideos.add(videoId);
    }

    public void addVideoToHistory(String videoId) {
        videoHistory.add(videoId);
    }

    public void addToSubscribedToUsers(String userId) {
        subscribedToUsers.add(userId);
    }

    public void addToSubscribers(String userId) {
        subscribes.add(userId);
    }

    public void removeFromSubscribedToUsers(String userId) {
        subscribedToUsers.remove(userId);
    }

    public void removeFromSubscribers(String userId) {
        subscribes.remove(userId);
    }
}
