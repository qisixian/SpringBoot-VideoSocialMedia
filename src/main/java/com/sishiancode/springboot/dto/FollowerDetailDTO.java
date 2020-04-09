package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FollowerDetailDTO {
    String userId;
    String username;
    String avatarId;
    LocalDateTime localDateTime;
}
