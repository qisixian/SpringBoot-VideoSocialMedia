package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.PostLike;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends MongoRepository<PostLike, String> {
    List<PostLike> findByPostUserId(String postUserId);

    <T> List<T> findByPostUserId(String postUserId, Class<T> type);

    List<PostLike> findByLikedUserId(String likedUserId);

    <T> List<T> findByLikedUserId(String likedUserId, Class<T> type);

    Integer countByLikedUserId(String likedUserId);

    Integer countByPostId(String postId);

    void deleteByPostId(String postId);

    PostLike findByPostIdAndLikedUserId(String postId, String likedUserId);

    void deleteByPostIdAndLikedUserId(String postId, String likedUserId);
}
