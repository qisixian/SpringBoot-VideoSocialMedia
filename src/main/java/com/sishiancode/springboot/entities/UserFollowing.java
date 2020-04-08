package com.sishiancode.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userFollowing")
public class UserFollowing {
    @Id
    private String id;
    @Indexed(background = true)
    private String userId;
    @Indexed(background = true)
    private String followingId;
    private LocalDateTime localDateTime;

    public UserFollowing(String userId, String followingId, LocalDateTime localDateTime) {
        this.userId = userId;
        this.followingId = followingId;
        this.localDateTime = localDateTime;
    }
}
