package com.kubekthecreator.projects.youtubeclone.model;

import com.kubekthecreator.projects.youtubeclone.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;
    private String text;
    private String authorId;
    private Integer likesCount;
    private Integer dislikeCount;

    @Transient
    public CommentDto toDto() {
        return new CommentDto(this);
    }
}
