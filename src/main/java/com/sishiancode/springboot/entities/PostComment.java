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
@Document(collection = "postComment")
public class PostComment {
    @Id
    private String id;
    private String postId;
    private String content;
    private String senderId;
    @Indexed(background = true)
    private String receiverId;
    private LocalDateTime localDateTime;

    public PostComment(String postId, String content, String senderId, String receiverId, LocalDateTime localDateTime) {
        this.postId = postId;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.localDateTime = localDateTime;
    }
}
