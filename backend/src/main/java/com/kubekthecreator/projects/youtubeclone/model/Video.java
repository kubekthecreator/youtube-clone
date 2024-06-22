package com.kubekthecreator.projects.youtubeclone.model;

import com.kubekthecreator.projects.youtubeclone.dto.CommentDto;
import com.kubekthecreator.projects.youtubeclone.dto.VideoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private AtomicInteger likes = new AtomicInteger(0);
    private AtomicInteger dislikes = new AtomicInteger(0);
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private AtomicInteger viewCount = new AtomicInteger(0);;
    private String thumbnailUrl;
    private List<Comment> commentList = new CopyOnWriteArrayList<>();

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
        this.setViewCount(new AtomicInteger(videoDto.getViewCount()));
        this.setThumbnailUrl(videoDto.getThumbnailUrl());
        this.setCommentList(videoDto.getCommentList());
    }

    public void incrementViewCount() {
        if (this.viewCount.get() > 0) {
            this.viewCount.incrementAndGet();
        }
    }

    public void addComment(CommentDto commentDto) {
        commentList.add(commentDto.asEntity());
    }
}
