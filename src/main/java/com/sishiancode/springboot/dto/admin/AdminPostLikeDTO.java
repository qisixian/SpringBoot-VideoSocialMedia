package com.sishiancode.springboot.dto.admin;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AdminPostLikeDTO {
    String id;
    String postId;
    String likedUserId;
    ;
    String likedUsername;
    LocalDateTime localDateTime;
}

