package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
    <T> T findById(String id, Class<T> type);

    List<Post> findByIdIn(List<String> idList);

    //    @Query("{'userId: ?0'}")
    List<Post> findByUserId(String userId);

    <T> List<T> findByUserId(String userId, Class<T> type);

    //    怎么用一个集合来查询？
//    List<Post> findByUserId(List<String> userId);
    List<Post> findByUserIdIn(List<String> userIdList);

    Integer countByUserId(String userId);

//    @Query("{'userId: ?0'}")
//    Page<Post> findByUserId(String userId, Pageable pageable);

    void deleteByUserId(String userId);

}
