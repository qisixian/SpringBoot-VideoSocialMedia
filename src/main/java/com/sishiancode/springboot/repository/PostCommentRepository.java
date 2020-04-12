package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.entities.PostComment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends MongoRepository<PostComment, String> {

    List<PostComment> findByReceiverId(String receiverId);

    List<PostComment> findByReceiverIdOrderByLocalDateTimeDesc(String receiverId);

    List<PostComment> findByPostId(String postId);

    List<PostComment> findByPostIdOrderByLocalDateTimeAsc(String postId);

    void deleteByPostId(String postId);
}
