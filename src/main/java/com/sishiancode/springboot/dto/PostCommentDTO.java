package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PostCommentDTO {
    String id;
    //    String postId;
    String content;
    String senderId;
    String senderName;
    String receiverName;
    LocalDateTime localDateTime;

}
