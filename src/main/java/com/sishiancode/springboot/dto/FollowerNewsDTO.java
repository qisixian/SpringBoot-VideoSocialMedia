package com.sishiancode.springboot.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class FollowerNewsDTO {
    String userId;
    LocalDateTime localDateTime;
}
