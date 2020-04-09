package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    Profile findByUserId(String userId);

    <T> T findByUserId(String followingId, Class<T> type);

    List<Profile> findByUserIdIn(List<String> userId);

    Profile findByUsername(String username);

    //    ProfileDTO findByUserId(String userId);
    Integer countByUserId(String userId);

    //    UserProfileDTO save
    void deleteByUserId(String userId);
}
