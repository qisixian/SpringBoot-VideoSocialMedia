package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class CommentDetailDTO {
    String postId;
    String content;
    String senderId;
    String senderName;
    String senderAvatarId;
    String receiverName;
    LocalDateTime localDateTime;

}
