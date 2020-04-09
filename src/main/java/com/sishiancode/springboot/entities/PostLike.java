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
@Document(collection = "postLike")
public class PostLike {
    @Id
    private String id;
//    private String postUserId;

    @Indexed(background = true)
    private String postId;
    @Indexed(background = true)
    private String likedUserId;
    private LocalDateTime localDateTime;

    public PostLike(String postId, String likedUserId, LocalDateTime localDateTime) {
        this.postId = postId;
        this.likedUserId = likedUserId;
        this.localDateTime = localDateTime;
    }
}
