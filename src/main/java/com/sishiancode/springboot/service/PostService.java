package com.sishiancode.springboot.service;

import com.sishiancode.springboot.dto.PostCommentDTO;
import com.sishiancode.springboot.entities.Post;
import com.sishiancode.springboot.entities.PostComment;
import com.sishiancode.springboot.entities.PostLike;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService extends BaseService {

    public Post findPost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        return post.get();
    }

    //未测试
    public List<PostCommentDTO> findPostComment(String postId) {
        List<PostComment> postCommentList = postCommentRepository.findByPostId(postId);
        List<PostCommentDTO> postCommentDTOList = new ArrayList<>();
        for (PostComment postComment : postCommentList) {
            postCommentDTOList.add(new PostCommentDTO(postComment.getPostId(), postComment.getContent(), postComment.getSenderId(), postComment.getReceiverId(), postComment.getLocalDateTime()));
        }
        return postCommentDTOList;
    }

    public Integer findPostLikesCount(String postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public boolean findIsLiked(String postId, String likedUserId) {
        return postLikeRepository.findByPostIdAndLikedUserId(postId, likedUserId) != null;
    }

    public void addLike(String postUserId, String postId, String likedUserId) {
        if (findIsLiked(postId, likedUserId) == false) {
            PostLike postLike = new PostLike(postUserId, postId, likedUserId, LocalDateTime.now());
            postLikeRepository.save(postLike);
        }
    }

    public void unLike(String postId, String likedUserId) {
        postLikeRepository.deleteByPostIdAndLikedUserId(postId, likedUserId);
    }
}
