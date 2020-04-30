package com.sishiancode.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "post")
public class Post {
    @Id
    private String id;
    @Indexed(background = true)
    private String userId;
    private String username;
    private String describe;
    private String videoId;
    private LocalDateTime updateTime;

    public Post(String userId, String username, String describe, String videoId, LocalDateTime updateTime) {
        this.userId = userId;
        this.username = username;
        this.describe = describe;
        this.videoId = videoId;
        this.updateTime = updateTime;
    }
}

