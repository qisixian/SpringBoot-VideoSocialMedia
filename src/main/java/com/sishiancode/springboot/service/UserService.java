package com.sishiancode.springboot.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.sishiancode.springboot.dto.FollowerIdDTO;
import com.sishiancode.springboot.dto.FollowingIdDTO;
import com.sishiancode.springboot.entities.Post;
import com.sishiancode.springboot.entities.Profile;
import com.sishiancode.springboot.entities.User;
import com.sishiancode.springboot.entities.UserFollowing;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends BaseService {

    public Profile findProfileByUserId(String userId) {
        return profileRepository.findByUserId(userId);
    }

    public String saveProfile(Profile profile) {
        return profileRepository.save(profile).getId();
    }

    public void updateAvatar(MultipartFile file, String userId) throws IOException {
        //存入头像
        DBObject metaData = new BasicDBObject();
        metaData.put("upLoadUserId", userId);
        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
        //关联头像和用户
        Profile profile = findProfileByUserId(userId);
        String oldAvatarId = profile.getAvatarId();
        profile.setAvatarId(id.toString());
        saveProfile(profile);

        //在数据库删除之前的头像
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(oldAvatarId)));
    }

    public InputStream getAvatarStream(String avatarId) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(avatarId)));

        return gridFsOperations.getResource(file).getInputStream();
    }

    public void addPost(String userId, String describe, MultipartFile file) throws IOException {
        //存入视频
        DBObject metaData = new BasicDBObject();
        metaData.put("upLoadUserId", userId);
        ObjectId id = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metaData);
        //创建对应Post
        Optional<User> user = userRepository.findById(userId);
        Post post = new Post(userId, user.get().getUsername(), describe, id.toString(), LocalDateTime.now());
        postRepository.save(post);
    }

    public void deletePost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        String videoId = post.get().getVideoId();
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(videoId)));
        postRepository.deleteById(postId);
    }

    public InputStream getVideoStream(String videoId) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(videoId)));

        return gridFsOperations.getResource(file).getInputStream();
    }

    public boolean findIsFollowed(String followerId, String followingId) {
        return userFollowingRepository.findByUserIdAndFollowingId(followerId, followingId) != null;
    }

    public void addFollow(String followerId, String followingId) {
        if (findIsFollowed(followerId, followingId) == false) {
            UserFollowing userFollowing = new UserFollowing(followerId, followingId, LocalDateTime.now());
            userFollowingRepository.save(userFollowing);
        }
    }

    public void unFollow(String followerId, String followingId) {
        userFollowingRepository.deleteByUserIdAndFollowingId(followerId, followingId);
    }

    //    public List<String> findFollowingListId(String followerId) {
//        userFollowingRepository
//    }
    public List<String> findFollowingIdList(String userId) {
        List<FollowingIdDTO> followingList = userFollowingRepository.findByUserId(userId, FollowingIdDTO.class);
//        需要按localDateTime排序
//        需不需要返回username，头像，签名，？（关注数量？）
        List<String> followingIdList = new ArrayList<>();
//        for (int i = 0; i < followingList.size(); i++) {
        for (FollowingIdDTO followingIdDTO : followingList) {
            followingIdList.add(followingIdDTO.getFollowingId());
        }
        return followingIdList;
    }

    public List<Profile> findFollowingProfileList(String userId) {
        List<String> idList = findFollowingIdList(userId);
        logger.debug(idList.toString());
        List<Profile> profileList;
        profileList = profileRepository.findByUserIdIn(idList);

        return profileList;
    }

    public List<String> findFollowerIdList(String followingId) {
        List<FollowerIdDTO> followerList = userFollowingRepository.findByFollowingId(followingId, FollowerIdDTO.class);

        List<String> followerIdList = new ArrayList<>();
//        for (int i = 0; i < followingList.size(); i++) {
        for (FollowerIdDTO followerIdDTO : followerList) {
            followerIdList.add(followerIdDTO.getUserId());
        }
        return followerIdList;
    }

    public List<Profile> findFollowerProfileList(String followingId) {
        List<String> idList = findFollowerIdList(followingId);
        logger.debug(idList.toString());
        List<Profile> profileList;
        profileList = profileRepository.findByUserIdIn(idList);

        return profileList;
    }
}
