package com.kubekthecreator.projects.youtubeclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kubekthecreator.projects.youtubeclone.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    @JsonProperty("sub")
    private String sub;
    @JsonProperty("given_name")
    private String givenName;
    @JsonProperty("family_name")
    private String familyName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("picture")
    private String picture;
    private String email;

    public User asEntity() {
        User user = new User();
        user.setId(this.id);
        user.setFirstName(this.givenName);
        user.setLastName(this.familyName);
        user.setFullName(this.name);
        user.setEmailAddress(this.email);
        user.setSub(this.sub);
        user.setSubscribedToUsers(new HashSet<>());
        user.setSubscribes(new HashSet<>());
        user.setVideoHistory(new ArrayList<>());
        user.setLikedVideos(new HashSet<>());
        user.setDislikedVideos(new HashSet<>());
        return user;
    }
}
