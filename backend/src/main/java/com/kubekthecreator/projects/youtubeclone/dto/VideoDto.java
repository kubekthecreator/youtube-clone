package com.kubekthecreator.projects.youtubeclone.dto;


import com.kubekthecreator.projects.youtubeclone.model.Video;
import com.kubekthecreator.projects.youtubeclone.model.VideoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    private String id;
    private String title;
    private String description;
    private Set<String> tags;
    private String videoUrl;
    private VideoStatus videoStatus;
    private String thumbnailUrl;

    public VideoDto(Video video) {
        this.id = video.getId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.tags = video.getTags();
        this.videoUrl = video.getVideoUrl();
        this.videoStatus = video.getVideoStatus();
        this.thumbnailUrl = video.getThumbnailUrl();
    }
}
