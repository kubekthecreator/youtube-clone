package com.kubekthecreator.projects.youtubeclone.dto;

import com.kubekthecreator.projects.youtubeclone.model.Comment;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private String id;
    private String text;
    private String authorId;
    private Integer likesCount;
    private Integer dislikeCount;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.authorId = comment.getAuthorId();
        this.likesCount = comment.getLikesCount();
        this.dislikeCount = comment.getDislikeCount();
    }

    public Comment asEntity() {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setText(text);
        comment.setAuthorId(authorId);
        comment.setLikesCount(likesCount);
        comment.setDislikeCount(dislikeCount);
        return comment;
    }
}
