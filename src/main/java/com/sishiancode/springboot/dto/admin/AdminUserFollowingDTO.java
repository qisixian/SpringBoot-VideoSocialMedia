package com.sishiancode.springboot.dto.admin;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class AdminUserFollowingDTO {
    String id;
    String userId;
    String username;
    String followingId;
    String followingUsername;
    LocalDateTime localDateTime;
}
