package com.sishiancode.springboot.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sishiancode.springboot.dto.admin.AdminPostCommentDTO;
import com.sishiancode.springboot.dto.admin.AdminPostLikeDTO;
import com.sishiancode.springboot.dto.admin.AdminUserFollowingDTO;
import com.sishiancode.springboot.entities.*;
import org.bson.types.ObjectId;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService extends BaseService {

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User findUserById(String userId) {
        return userRepository.findById(userId).get();
    }

    public String saveUser(User user) {
        if (!user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getPhoneNumber().isEmpty()) {
            User userWithId = userRepository.save(user);
            //save是不分新用户还是已存在的用户的
            //用userId找有没有已经存在的profile
            Profile existProfile = profileRepository.findByUserId(userWithId.getId());
            if (existProfile != null) {
                logger.debug(existProfile.toString());
                existProfile.setUsername(user.getUsername());
                existProfile.setPhoneNumber(user.getPhoneNumber());
                profileRepository.save(existProfile);
            } else {
//        创建关联的profile
                ObjectId id = new ObjectId();
                try (InputStream is = new BufferedInputStream(new ClassPathResource("static/default-avatar.png").getInputStream())) {
                    // store file
                    DBObject metaData = new BasicDBObject();
                    metaData.put("upLoadUserId", userWithId.getId());
                    id = gridFsTemplate.store(is, "default-avatar.png", "image/png", metaData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Profile newProfile = new Profile((userWithId.getId()), userWithId.getUsername(), userWithId.getPhoneNumber(), id.toString());
                logger.debug(newProfile.toString());
                profileRepository.save(newProfile);
            }
            return userWithId.getId();
        } else {
            return null;
        }
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
//        删除关联的profile
        profileRepository.deleteByUserId(userId);
//        删除关联的post
        postRepository.deleteByUserId(userId);
    }


    public List<Administrator> findAllAdmin() {
        return administratorRepository.findAll();
    }

    public Administrator findAdminById(String adminId) {
        return administratorRepository.findById(adminId).get();
    }

    public String saveAdmin(Administrator administrator) {
        if (!administrator.getUsername().isEmpty() && !administrator.getPassword().isEmpty() && !administrator.getPhoneNumber().isEmpty()) {
            return administratorRepository.save(administrator).getId();
        } else {
            return null;
        }
    }

    public void deleteAdmin(String adminId) {
        administratorRepository.deleteById(adminId);
    }


    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    public Post findPostById(String postId) {
        return postRepository.findById(postId).get();
    }

    public String savePost(Post post) {
        return postRepository.save(post).getId();
    }

    public void deletePost(String postId) {
        postRepository.deleteById(postId);
        //删除关联的评论和喜欢
        postCommentRepository.deleteByPostId(postId);
        postLikeRepository.deleteByPostId(postId);
    }


    public List<PostComment> findAllPostComment() {
        return postCommentRepository.findAll();
    }

    public List<AdminPostCommentDTO> findAllPostCommentDTO() {
        List<PostComment> postCommentList = findAllPostComment();
        List<AdminPostCommentDTO> adminPostCommentDTOList = new ArrayList<>();
        for (PostComment postComment :
                postCommentList) {
            String senderUsername = profileRepository.findByUserId(postComment.getSenderId()).getUsername();
            String receiverUsername = profileRepository.findByUserId(postComment.getReceiverId()).getUsername();
            adminPostCommentDTOList.add(new AdminPostCommentDTO(postComment.getId(), postComment.getPostId(), postComment.getContent(), postComment.getSenderId(), senderUsername, postComment.getReceiverId(), receiverUsername, postComment.getLocalDateTime()));
        }
        return adminPostCommentDTOList;
    }

    public PostComment findPostCommentById(String postCommentId) {
        return postCommentRepository.findById(postCommentId).get();
    }

    public String savePostComment(PostComment postComment) {
//        logger.debug("savePostComment:"+postComment.toString());
//        logger.debug(String.valueOf(postRepository.findById(postComment.getPostId()).isPresent()));
//        logger.debug(String.valueOf(userRepository.findById(postComment.getSenderId()).isPresent()));
//        logger.debug(String.valueOf(userRepository.findById(postComment.getReceiverId()).isPresent()));
//        logger.debug(postComment.getContent());
        if (postRepository.findById(postComment.getPostId()).isPresent() && userRepository.findById(postComment.getSenderId()).isPresent() && userRepository.findById(postComment.getReceiverId()).isPresent() && !postComment.getContent().isEmpty()) {
            postComment.setLocalDateTime(LocalDateTime.now());
            return postCommentRepository.save(postComment).getId();
        } else {
            return null;
        }
    }

    public void deletePostComment(String postCommentId) {
        postCommentRepository.deleteById(postCommentId);
    }


    public List<PostLike> findAllPostLike() {
        return postLikeRepository.findAll();
    }

    public List<AdminPostLikeDTO> findAllPostLikeDTO() {
        List<PostLike> postLikeList = findAllPostLike();
        List<AdminPostLikeDTO> adminPostLikeDTOList = new ArrayList<>();
        for (PostLike postLike :
                postLikeList) {
            String likedUsername = profileRepository.findByUserId(postLike.getLikedUserId()).getUsername();
            adminPostLikeDTOList.add(new AdminPostLikeDTO(postLike.getId(), postLike.getPostId(), postLike.getLikedUserId(), likedUsername, postLike.getLocalDateTime()));
        }
        return adminPostLikeDTOList;
    }

    public PostLike findPostLikeById(String postLikeId) {
        return postLikeRepository.findById(postLikeId).get();
    }

    public String savePostLike(PostLike postLike) {
        if (postRepository.findById(postLike.getPostId()).isPresent() && userRepository.findById(postLike.getLikedUserId()).isPresent()) {
            postLike.setLocalDateTime(LocalDateTime.now());
            return postLikeRepository.save(postLike).getId();
        } else {
            return null;
        }
    }

    public void deletePostLike(String postLikeId) {
        postLikeRepository.deleteById(postLikeId);
    }


    public List<Profile> findAllProfile() {
        return profileRepository.findAll();
    }

    public Profile findProfileById(String profileId) {
        return profileRepository.findById(profileId).get();
    }

    public String saveProfile(Profile profile) {

        return profileRepository.save(profile).getId();
    }

    public void deleteProfile(String profileId) {
        profileRepository.deleteById(profileId);
    }


    public List<UserFollowing> findAllUserFollowing() {
        return userFollowingRepository.findAll();
    }

    public List<AdminUserFollowingDTO> findAllUserFollowingDTO() {
        List<UserFollowing> userFollowingList = findAllUserFollowing();
        List<AdminUserFollowingDTO> adminUserFollowingDTOList = new ArrayList<>();
        for (UserFollowing userFollowing :
                userFollowingList) {
            String username = profileRepository.findByUserId(userFollowing.getUserId()).getUsername();
            String followingUsername = profileRepository.findByUserId(userFollowing.getFollowingId()).getUsername();
            adminUserFollowingDTOList.add(new AdminUserFollowingDTO(userFollowing.getId(), userFollowing.getUserId(), username, userFollowing.getFollowingId(), followingUsername, userFollowing.getLocalDateTime()));
        }
        return adminUserFollowingDTOList;
    }

    public UserFollowing findUserFollowingById(String userFollowingId) {
        return userFollowingRepository.findById(userFollowingId).get();
    }

    public String saveUserFollowing(UserFollowing userFollowing) {
        if (userRepository.findById(userFollowing.getUserId()).isPresent() && userRepository.findById(userFollowing.getFollowingId()).isPresent()) {
            userFollowing.setLocalDateTime(LocalDateTime.now());
            return userFollowingRepository.save(userFollowing).getId();
        } else {
            return null;
        }
    }

    public void deleteUserFollowing(String userFollowingId) {
        userFollowingRepository.deleteById(userFollowingId);
    }
}
