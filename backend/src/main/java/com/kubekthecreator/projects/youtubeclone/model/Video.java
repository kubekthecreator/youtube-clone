package com.kubekthecreator.projects.youtubeclone.model;

import com.kubekthecreator.projects.youtubeclone.dto.VideoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Document(value = "Video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private AtomicInteger likes;
    private AtomicInteger dislikes;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private Integer viewCount;
    private String thumbnailUrl;
    private List<Comment> commentList;

    @Transient
    public VideoDto toDto() {
        return new VideoDto(this);
    }

    public void increaseLikes() {
        this.likes.incrementAndGet();
    }

    public void decreaseLikes() {
        if (this.likes.get() > 0) {
            this.likes.decrementAndGet();
        }
    }

    public void increaseDislikes() {
        this.dislikes.incrementAndGet();
    }

    public void decreaseDislikes() {
        if (this.dislikes.get() > 0) {
            this.dislikes.decrementAndGet();
        }
    }

    public void fillFromDto(VideoDto videoDto) {
        this.setId(videoDto.getId());
        this.setTitle(videoDto.getTitle());
        this.setDescription(videoDto.getDescription());
        this.setUserId(videoDto.getUserId());
        this.setLikes(new AtomicInteger(videoDto.getLikes()));
        this.setDislikes(new AtomicInteger(videoDto.getDislikes()));
        this.setTags(videoDto.getTags());
        this.setVideoUrl(videoDto.getVideoUrl());
        this.setVideoStatus(videoDto.getVideoStatus());
        this.setViewCount(videoDto.getViewCount());
        this.setThumbnailUrl(videoDto.getThumbnailUrl());
        this.setCommentList(videoDto.getCommentList());
    }
}
