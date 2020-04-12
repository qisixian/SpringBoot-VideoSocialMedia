package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.UserFollowing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowingRepository extends MongoRepository<UserFollowing, String> {

    List<UserFollowing> findByFollowingId(String followingId);


    List<UserFollowing> findByFollowingIdOrderByLocalDateTimeDesc(String followingId);

    <T> List<T> findByFollowingId(String followingId, Class<T> type);

    <T> List<T> findByFollowingIdOrderByLocalDateTimeDesc(String followingId, Class<T> type);


    //    怎么限制查询只返回一个String列？
    List<UserFollowing> findByUserId(String userId);

    <T> List<T> findByUserId(String userId, Class<T> type);

    <T> List<T> findByUserIdOrderByLocalDateTimeDesc(String userId, Class<T> type);

//    List<FollowingIdDTO> findByUserId(String userId);
//    showFollowingList需要返回一个按时间排列的followingId
//    showFollowingPost需要返回不需要排序的followingId


    Integer countByUserId(String userId);

    Integer countByFollowingId(String followingId);

    UserFollowing findByUserIdAndFollowingId(String userId, String followingId);

    void deleteByUserIdAndFollowingId(String userId, String followingId);

}
