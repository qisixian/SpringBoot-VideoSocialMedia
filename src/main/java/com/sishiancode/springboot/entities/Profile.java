package com.sishiancode.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profile")
public class Profile {
    @Id
    private String id;
    //    怎么把userId和profileId整合到一块？
    @Indexed(background = true)
    private String userId;
    private String username;
    private String phoneNumber;
    private String email;
    private String avatarId;
    private String gender;
    //    private LocalDate birthDate;
    private String signature;

    public Profile(String userId, String username, String phoneNumber, String email, String avatarId, String gender, String signature) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.avatarId = avatarId;
        this.gender = gender;
//        this.birthDate = birthDate;
        this.signature = signature;
    }

    public Profile(String userId, String username, String phoneNumber, String avatarId) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.avatarId = avatarId;
    }
}
