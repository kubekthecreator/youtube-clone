package com.kubekthecreator.projects.youtubeclone.dto;


import com.kubekthecreator.projects.youtubeclone.model.Comment;
import com.kubekthecreator.projects.youtubeclone.model.Video;
import com.kubekthecreator.projects.youtubeclone.model.VideoStatus;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private String thumbnailUrl;
    private Integer likes;
    private Integer dislikes;
    private String userId;
    private Integer viewCount;
    private List<Comment> commentList;

    public VideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.tags = video.getTags();
        this.videoUrl = video.getVideoUrl();
        this.videoStatus = video.getVideoStatus();
        this.thumbnailUrl = video.getThumbnailUrl();
        this.likes = video.getLikes().get();
        this.dislikes = video.getDislikes().get();
        this.userId = video.getUserId();
        this.viewCount = video.getViewCount().get();
        this.commentList = video.getCommentList();
    }
}
