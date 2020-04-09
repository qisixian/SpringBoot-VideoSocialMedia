package com.sishiancode.springboot.service;

import com.sishiancode.springboot.dto.*;
import com.sishiancode.springboot.entities.PostComment;
import com.sishiancode.springboot.entities.PostLike;
import com.sishiancode.springboot.entities.UserFollowing;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService extends BaseService {
    public List<CommentDetailDTO> findAllComment(String userId) {
        List<PostComment> postCommentList = postCommentRepository.findByReceiverId(userId);
        List<CommentDetailDTO> commentDetailDTOList = new ArrayList<>();
        for (PostComment postComment : postCommentList) {
            AvatarAndNameDTO profile = profileRepository.findByUserId(postComment.getSenderId(), AvatarAndNameDTO.class);
            commentDetailDTOList.add(new CommentDetailDTO(postComment.getPostId(), postComment.getContent(), postComment.getSenderId(), profile.getUsername(), profile.getAvatarId(), postComment.getReceiverId(), postComment.getLocalDateTime()));
        }
        return commentDetailDTOList;

    }

    public List<LikedDetailDTO> findAllLikes(String userId) {
        List<IdDTO> postIdDTOList = postRepository.findByUserId(userId, IdDTO.class);
//        logger.debug(postIdDTOList.toString());
        List<String> postIdList = new ArrayList<>();
        for (IdDTO postIdDTO : postIdDTOList) {
            postIdList.add(postIdDTO.getId());
        }
//        logger.debug(postIdList.toString());
        List<PostLike> postLikeList = postLikeRepository.findByPostIdIn(postIdList);
//        logger.debug(postLikeList.toString());
        List<LikedDetailDTO> likedDetailDTOList = new ArrayList<>();
        for (PostLike postLike : postLikeList) {
            AvatarAndNameDTO profile = profileRepository.findByUserId(postLike.getLikedUserId(), AvatarAndNameDTO.class);
            likedDetailDTOList.add(new LikedDetailDTO(postLike.getPostId(), postLike.getLikedUserId(), profile.getUsername(), profile.getAvatarId(), postLike.getLocalDateTime()));
        }
//        logger.debug(likedDetailDTOList.toString());
//        排序问题待考虑
        return likedDetailDTOList;
    }

    public List<FollowerDetailDTO> findAllFollower(String userId) {
        List<UserFollowing> userFollowingList = userFollowingRepository.findByFollowingId(userId);
        List<FollowerDetailDTO> followerDetailDTOList = new ArrayList<>();
        for (UserFollowing userFollowing : userFollowingList) {
            AvatarAndNameDTO profile = profileRepository.findByUserId(userFollowing.getUserId(), AvatarAndNameDTO.class);
            followerDetailDTOList.add(new FollowerDetailDTO(userFollowing.getUserId(), profile.getUsername(), profile.getAvatarId(), userFollowing.getLocalDateTime()));
        }
//        排序问题待考虑
        return followerDetailDTOList;
    }

}
