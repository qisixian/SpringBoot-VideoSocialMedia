package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LikedDetailDTO {
    String postId;
    String likedUserId;
    String likedUsername;
    String likedUserAvatarId;
    LocalDateTime localDateTime;

}
