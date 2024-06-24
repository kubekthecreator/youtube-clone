package com.kubekthecreator.projects.youtubeclone.service;

import com.kubekthecreator.projects.youtubeclone.model.User;
import com.kubekthecreator.projects.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Object sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
        return userRepository.findBySub(sub).orElseThrow(() -> new IllegalStateException("User not found with sub " + sub));
    }

    public void addToLikedVideos(String videoId) {
        User user = getCurrentUser();
        user.addToLikedVideos(videoId);
        userRepository.save(user);
    }

    public boolean ifLikedVideo(String videoId) {
        return getCurrentUser().getLikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public boolean ifDislikedVideo(String videoId) {
        return getCurrentUser().getDislikedVideos().stream().anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    public void removeFromLikedVideos(String videoId) {
        User user = getCurrentUser();
        user.removeFromLikedVideos(videoId);
        userRepository.save(user);
    }

    public void removeFromDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.removeFromDislikedVideos(videoId);
        userRepository.save(user);
    }

    public void addToDislikedVideos(String videoId) {
        User user = getCurrentUser();
        user.addToDislikedVideos(videoId);
        userRepository.save(user);
    }

    public void addVideoToHistory(String videoId) {
        User user = getCurrentUser();
        user.addVideoToHistory(videoId);
        userRepository.save(user);
    }

    public void subscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.addToSubscribedToUsers(userId);

        User user = getUserById(userId);
        user.addToSubscribers(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(user);
    }

    public void unSubscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromSubscribedToUsers(userId);

        User user = getUserById(userId);
        user.removeFromSubscribers(currentUser.getId());

        userRepository.save(currentUser);
        userRepository.save(user);
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User not found with id " + userId));
    }

    public Set<String> userHistory(String userId) {
        return getUserById(userId).getVideoHistory();
    }
}
