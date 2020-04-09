package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PostAllDetailDTO {
    String id;
    String userId;
    String username;
    String avatarId;
    String describe;
    String videoId;
    LocalDateTime updateTime;
    Integer likesCount;
    Boolean isLiked;
}
