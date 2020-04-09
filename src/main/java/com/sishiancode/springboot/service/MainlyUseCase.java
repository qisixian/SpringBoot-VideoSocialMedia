package com.sishiancode.springboot.service;

import com.sishiancode.springboot.dto.FollowerIdDTO;
import com.sishiancode.springboot.dto.FollowingIdDTO;
import com.sishiancode.springboot.dto.ShowProfileDTO;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainlyUseCase extends BaseService {

    @Autowired
    protected PostService postService;

//    public List<PostWithProfileDTO> showFollowingPost(String userId) {
//
////        先要查找following
//        List<FollowingIdDTO> followingList = userFollowingRepository.findByUserId(userId, FollowingIdDTO.class);
////        要把List<FollowingIdDTO>转化为List<String>;
//        List<String> userIdList = new ArrayList<>();
//        for (FollowingIdDTO followingIdDTO : followingList) {
//            userIdList.add(followingIdDTO.getFollowingId());
//        }
////        logger.debug(userIdList.toString());
//        List<Post> followingPosts = new ArrayList<>();
//        followingPosts = postRepository.findByUserIdIn(userIdList);
//
//        List<PostWithProfileDTO> postWithProfileList = new ArrayList<>();
//        for (Post post : followingPosts) {
//            String avatarId = findAvatarId(post.getUserId());
//            Integer likesCount = postService.findPostLikesCount(post.getId());
////            Integer isLiked;
////            if(postService.findIsLiked(post.getId(), userId)==true){
////                isLiked =1;
////            }else {
////                isLiked = 0;
////            }
//            Boolean isLiked = postService.findIsLiked(post.getId(), userId);
//            postWithProfileList.add(new PostWithProfileDTO(post.getId(), post.getUserId(), post.getUsername(), avatarId, post.getDescribe(), post.getVideoId(), post.getUpdateTime(), likesCount, isLiked));
//        }
//
////        Aggregation.newAggregation(Aggregation.match(Criteria))
//
//        //List<PostDTO> followingPostDTOs = new ArrayList<>();
//        return postWithProfileList;
//    }
//
//    public String findAvatarId(String userId) {
//        AvatarIdDTO avatarIdDTO = profileRepository.findByUserId(userId, AvatarIdDTO.class);
//        return avatarIdDTO.getAvatarId();
//    }

//    public Post uploadPost(PostDTO postDTO) {
//        //controller要不要看到entity？(感觉这才是DTO的作用？)
//
//    }


//    public List<PostComment> showCommentsNews(String userId) {
//        List<PostComment> commentList = postCommentRepository.findByReceiverId(userId);
////        响应模型目前相同
//        return commentList;
//    }
//
//    public List<LikedNewsDTO> showLikedNews(String userId) {
//        List<PostIdDTO> postIdDTOList = postRepository.findByUserId(userId, PostIdDTO.class);
//        List<String> postIdList = new ArrayList<>();
//        for (PostIdDTO postIdDTO : postIdDTOList) {
//            postIdList.add(postIdDTO.getPostId());
//        }
//        List<LikedNewsDTO> likedNewsDTOList = postLikeRepository.findByPostIdIn(postIdList, LikedNewsDTO.class);
////        排序问题待考虑
//        return likedNewsDTOList;
//    }
//
//    public List<FollowerNewsDTO> newFollowersNews(String userId) {
//        List<FollowerNewsDTO> newFollowerList = userFollowingRepository.findByFollowingId(userId, FollowerNewsDTO.class);
////        排序问题待考虑
//        return newFollowerList;
//    }


    public Integer showFollowingCount(String userId) {
        Integer count = userFollowingRepository.countByUserId(userId);
        return count;
    }

    public List<FollowingIdDTO> showFollowingList(String userId) {
        List<FollowingIdDTO> followingList = userFollowingRepository.findByUserId(userId, FollowingIdDTO.class);
//        需要按localDateTime排序
//        需不需要返回username，头像，签名，？（关注数量？）
        return followingList;
    }

    public Integer showFollowersCount(String userId) {
        Integer count = userFollowingRepository.countByFollowingId(userId);
        return count;
    }

    public List<FollowerIdDTO> showFollowerList(String userId) {
        List<FollowerIdDTO> followersList = userFollowingRepository.findByFollowingId(userId, FollowerIdDTO.class);
//        需要按localDateTime排序
//        需不需要返回username，头像，签名，？（关注数量？）
        return followersList;
    }

    public Integer postsCount(String userId) {
        Integer count = postRepository.countByUserId(userId);
        return count;
    }


    public Integer showLikesCount(String userId) {
        Integer count = postLikeRepository.countByLikedUserId(userId);
        return count;
    }

//    public List<PostLike> showLikeList(String userId) {
//        List<PostLike> likeList = postLikeRepository.findByLikedUserId(userId);
////        不需要likedUserId，甚至是postUserId，排序好甚至不需要localDateTime
////        返回PostLike还是返回Post？
////        要不要返回一个postLikeRepository的DTO？返回DTO还能再连接到Post表查询吗？还是直接多表查询？
//        return likeList;
//    }

    public ShowProfileDTO showProfile(String userId) {
        Profile profile = profileRepository.findByUserId(userId);
        ShowProfileDTO showProfileDTO = new ShowProfileDTO();
        showProfileDTO.setUserId(profile.getUserId());
        showProfileDTO.setUsername(profile.getUsername());
        showProfileDTO.setPhoneNumber(profile.getPhoneNumber());
        showProfileDTO.setEmail(profile.getEmail());
        showProfileDTO.setAvatarId(profile.getAvatarId());
        showProfileDTO.setGender(profile.getGender());
//        showProfileDTO.setBirthDate(profile.getBirthDate());
        showProfileDTO.setSignature(profile.getSignature());
        showProfileDTO.setFollowingCount(showFollowingCount(userId));
        showProfileDTO.setFollowersCount(showFollowersCount(userId));
        showProfileDTO.setPostsCount(postsCount(userId));
        showProfileDTO.setLikesCount(showLikesCount(userId));
        return showProfileDTO;
    }
}
