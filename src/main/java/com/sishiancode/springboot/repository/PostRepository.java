package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    <T> T findById(String id, Class<T> type);

    List<Post> findByIdIn(List<String> idList);

    List<Post> findByUserId(String userId);

    List<Post> findByUserIdOrderByUpdateTimeDesc(String userId);

    <T> List<T> findByUserId(String userId, Class<T> type);

    List<Post> findByUserIdIn(List<String> userIdList);

    List<Post> findByUserIdInOrderByUpdateTimeDesc(List<String> userIdList);

    Integer countByUserId(String userId);

    void deleteByUserId(String userId);

}
