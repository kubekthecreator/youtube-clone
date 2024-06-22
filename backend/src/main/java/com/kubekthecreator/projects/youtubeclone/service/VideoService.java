package com.kubekthecreator.projects.youtubeclone.service;

import com.kubekthecreator.projects.youtubeclone.dto.CommentDto;
import com.kubekthecreator.projects.youtubeclone.dto.UploadVideoResponse;
import com.kubekthecreator.projects.youtubeclone.dto.VideoDto;
import com.kubekthecreator.projects.youtubeclone.model.Comment;
import com.kubekthecreator.projects.youtubeclone.model.Video;
import com.kubekthecreator.projects.youtubeclone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public UploadVideoResponse uploadVideo(MultipartFile file) {
        String videoUrl = s3Service.uploadFile(file);
        var video = new Video();
        video.setVideoUrl(videoUrl);
        var savedVideo = videoRepository.save(video);
        return new UploadVideoResponse(savedVideo.getId(),savedVideo.getVideoUrl());
    }

    public VideoDto editVideo(VideoDto videoDto) {
        var savedVideo = getVideById(videoDto.getId());
        savedVideo.fillFromDto(videoDto);
        videoRepository.save(savedVideo);
        return videoDto;
    }

    public String uploadThumbnail(MultipartFile file, String videoId) {
        var foundVideo = getVideById(videoId);
        String thumbnailUrl = s3Service.uploadFile(file);
        foundVideo.setThumbnailUrl(thumbnailUrl);
        videoRepository.save(foundVideo);
        return thumbnailUrl;
    }

    private Video getVideById(String videoId) {
        return videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video savedVideo = getVideById(videoId);
        increaseVideoCount(savedVideo);
        userService.addVideoToHistory(savedVideo.getId());
        return savedVideo.toDto();
    }

    private void increaseVideoCount(Video savedVideo) {
        savedVideo.incrementViewCount();
        videoRepository.save(savedVideo);
    }

    public VideoDto likeVideo(String videoId) {
        Video videById = getVideById(videoId);

        if (userService.ifLikedVideo(videoId)) {
            videById.decreaseLikes();
            userService.removeFromLikedVideos(videoId);
        } else if (userService.ifDislikedVideo(videoId)) {
            videById.decreaseDislikes();
            userService.removeFromDislikedVideos(videoId);
            videById.increaseLikes();
            userService.addToLikedVideos(videoId);
        } else {
            videById.increaseLikes();
            userService.addToLikedVideos(videoId);
        }
        videoRepository.save(videById);
        return videById.toDto();
    }

    public VideoDto dislikeVideo(String videoId) {
        Video videById = getVideById(videoId);

        if (userService.ifDislikedVideo(videoId)) {
            videById.decreaseDislikes();
            userService.removeFromDislikedVideos(videoId);
        } else if (userService.ifLikedVideo(videoId)) {
            videById.decreaseLikes();
            userService.removeFromLikedVideos(videoId);
            videById.increaseDislikes();
            userService.addToDislikedVideos(videoId);
        } else {
            videById.increaseDislikes();
            userService.addToDislikedVideos(videoId);
        }
        videoRepository.save(videById);
        return videById.toDto();
    }

    public void addComment(String videoId, CommentDto commentDto) {
        Video videById = getVideById(videoId);
        videById.addComment(commentDto);
        videoRepository.save(videById);
    }

    public List<CommentDto> getAllComments(String videoId) {
        Video videById = getVideById(videoId);
        List<Comment> commentList = videById.getCommentList();
        return commentList.stream().map(Comment::toDto).toList();
    }

    public List<VideoDto> getAllVideos() {
        return videoRepository.findAll().stream().map(Video::toDto).collect(Collectors.toList());
    }
}
