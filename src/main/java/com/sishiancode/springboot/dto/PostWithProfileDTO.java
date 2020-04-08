package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class PostWithProfileDTO {
    String id;
    String userId;
    String username;
    String avatarId;
    String describe;
    String videoId;
    LocalDateTime updateTime;
    Integer likesCount;
}
