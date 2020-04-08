package com.sishiancode.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowProfileDTO {
    //    private String id;
    String userId;
    String username;
    String phoneNumber;
    String email;
    String avatarId;
    String gender;
    //    LocalDate birthDate;
    String signature;
    Integer followingCount;
    Integer followersCount;
    Integer postsCount;
    Integer likesCount;

}
