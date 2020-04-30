package com.sishiancode.springboot.service;

import com.sishiancode.springboot.dto.*;
import com.sishiancode.springboot.entities.Post;
import com.sishiancode.springboot.entities.PostComment;
import com.sishiancode.springboot.entities.PostLike;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService extends BaseService {


    public List<PostAllDetailDTO> findFollowingPost(String userId) {
        List<FollowingIdDTO> followingList = userFollowingRepository.findByUserId(userId, FollowingIdDTO.class);
        List<String> userIdList = new ArrayList<>();
        for (FollowingIdDTO followingIdDTO : followingList) {
            userIdList.add(followingIdDTO.getFollowingId());
        }
        List<Post> followingPosts = postRepository.findByUserIdInOrderByUpdateTimeDesc(userIdList);
        List<PostAllDetailDTO> postAllDetailDTOList = new ArrayList<>();
        for (Post post : followingPosts) {
            String avatarId = findAvatarId(post.getUserId());
            Integer likesCount = findPostLikesCount(post.getId());
            Boolean isLiked = findIsLiked(post.getId(), userId);
            postAllDetailDTOList.add(new PostAllDetailDTO(post.getId(), post.getUserId(), post.getUsername(),
                    avatarId, post.getDescribe(), post.getVideoId(), post.getUpdateTime(), likesCount, isLiked));
        }
        return postAllDetailDTOList;
    }

    public List<PostDetailWithoutUserDTO> findUserPostList(String userId) {
        List<Post> postList = postRepository.findByUserIdOrderByUpdateTimeDesc(userId);
        List<PostDetailWithoutUserDTO> postDetailWithoutUserDTOList = new ArrayList<>();
        for (Post post : postList) {
            String avatarId = findAvatarId(post.getUserId());
            Integer likesCount = findPostLikesCount(post.getId());
            postDetailWithoutUserDTOList.add(new PostDetailWithoutUserDTO(post.getId(), post.getUserId(), post.getUsername(), avatarId, post.getDescribe(), post.getVideoId(), post.getUpdateTime(), likesCount));
        }

        return postDetailWithoutUserDTOList;
    }

    public String findAvatarId(String userId) {
        AvatarIdDTO avatarIdDTO = profileRepository.findByUserId(userId, AvatarIdDTO.class);
        return avatarIdDTO.getAvatarId();
    }

    public PostDetailWithoutUserDTO findPost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        String avatarId = findAvatarId(post.get().getUserId());
        Integer likesCount = findPostLikesCount(post.get().getId());
        PostDetailWithoutUserDTO postDetailWithoutUserDTO = new PostDetailWithoutUserDTO(post.get().getId(), post.get().getUserId(), post.get().getUsername(), avatarId, post.get().getDescribe(), post.get().getVideoId(), post.get().getUpdateTime(), likesCount);
        return postDetailWithoutUserDTO;
    }


    public Integer findPostLikesCount(String postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public boolean findIsLiked(String postId, String likedUserId) {
        return postLikeRepository.findByPostIdAndLikedUserId(postId, likedUserId) != null;
    }

    public void addLike(String postId, String likedUserId) {
        if (findIsLiked(postId, likedUserId) == false) {
            PostLike postLike = new PostLike(postId, likedUserId, LocalDateTime.now());
            postLikeRepository.save(postLike);
        }
    }

    public void unLike(String postId, String likedUserId) {
        postLikeRepository.deleteByPostIdAndLikedUserId(postId, likedUserId);
    }

    public List<Profile> findPostLikedUserProfile(String postId) {
        List<LikedUserIdDTO> likedUserIdList = postLikeRepository.findByPostId(postId, LikedUserIdDTO.class);
        List<Profile> likedUserProfileList = new ArrayList<>();
        for (LikedUserIdDTO likedUserId : likedUserIdList) {
            likedUserProfileList.add(profileRepository.findByUserId(likedUserId.getLikedUserId()));
        }
        return likedUserProfileList;
    }

    //未测试
    public List<PostCommentDTO> findPostComment(String postId) {
        List<PostComment> postCommentList = postCommentRepository.findByPostIdOrderByLocalDateTimeAsc(postId);
        List<PostCommentDTO> postCommentDTOList = new ArrayList<>();
        for (PostComment postComment : postCommentList) {
            UsernameDTO senderName = profileRepository.findByUserId(postComment.getSenderId(), UsernameDTO.class);
            UsernameDTO receiverName = profileRepository.findByUserId(postComment.getReceiverId(), UsernameDTO.class);
            postCommentDTOList.add(new PostCommentDTO(postComment.getId(), postComment.getContent(), postComment.getSenderId(), senderName.getUsername(), receiverName.getUsername(), postComment.getLocalDateTime()));
        }
        return postCommentDTOList;
    }

    public String addComment(String postId, String senderId, String content) {
        if (!content.isEmpty()) {
            String postUserId = postRepository.findById(postId).get().getUserId();
            PostComment postComment = new PostComment(postId, content, senderId, postUserId, LocalDateTime.now());
            return postCommentRepository.save(postComment).getId();
        } else {
            return null;
        }
    }

    public String addComment(String postId, String senderId, String receiverName, String content) {
        if (!content.isEmpty()) {
            Profile receiverProfile = profileRepository.findByUsername(receiverName);
            if (receiverProfile != null) {
                PostComment postComment = new PostComment(postId, content, senderId, receiverProfile.getUserId(), LocalDateTime.now());
                return postCommentRepository.save(postComment).getId();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void deleteComment(String postCommentId) {
        postCommentRepository.deleteById(postCommentId);
    }

    public List<PostAllDetailDTO> findLikePostList(String userId) {
        List<PostIdDTO> likePostIdDTOList = postLikeRepository.findByLikedUserIdOrderByLocalDateTimeDesc(userId, PostIdDTO.class);
//        List<PostLike> likePostIdDTOList = postLikeRepository.findByLikedUserIdOrderByLocalDateTimeDesc(userId, PostLike.class);
        //这是已经排好序的了

//        for (PostLike postLike :
//                likePostIdDTOList) {
//            logger.debug("time------>" + postLike.getLocalDateTime());
//
//        }
        List<String> likePostIdList = new ArrayList<>();
        for (PostIdDTO likePostIdDTO : likePostIdDTOList) {
            likePostIdList.add(likePostIdDTO.getPostId());
        }
        //List<Post> likePostList = postRepository.findByIdIn(likePostIdList);
        //在这又乱了

        List<PostAllDetailDTO> postAllDetailDTOList = new ArrayList<>();
        for (String postId : likePostIdList) {
            Post post = postRepository.findById(postId).get();
            String avatarId = findAvatarId(post.getUserId());
            Integer likesCount = findPostLikesCount(post.getId());
            Boolean isLiked = findIsLiked(post.getId(), userId);
            postAllDetailDTOList.add(new PostAllDetailDTO(post.getId(), post.getUserId(), post.getUsername(), avatarId, post.getDescribe(), post.getVideoId(), post.getUpdateTime(), likesCount, isLiked));
        }
        return postAllDetailDTOList;
    }
}
