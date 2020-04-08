package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class LikedNewsDTO {
    String postId;
    String likedUserId;
    LocalDateTime localDateTime;

}
