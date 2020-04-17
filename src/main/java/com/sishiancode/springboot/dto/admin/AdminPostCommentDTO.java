package com.sishiancode.springboot.dto.admin;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AdminPostCommentDTO {
    String id;
    String postId;
    String content;
    String senderId;
    String senderUsername;
    String receiverId;
    String receiverUsername;
    LocalDateTime localDateTime;
}
