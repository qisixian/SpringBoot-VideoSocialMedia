package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.PostAllDetailDTO;
import com.sishiancode.springboot.dto.PostDetailWithoutUserDTO;
import com.sishiancode.springboot.dto.ShowProfileDTO;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController extends BaseController {

    @GetMapping("/user/{id}")
    String toUser(@PathVariable("id") String userId, Model model, HttpSession session) {

        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        if (loginUserId.equals(userId)) {
            //如果是来到自己的页面返回user/me
            logger.trace("toMe:" + userId);

            ShowProfileDTO showProfileDTO = mainlyUseCase.showProfile(userId);
            logger.trace("toMe:showProfileDTO " + showProfileDTO.toString());
            model.addAttribute("showProfileDTO", showProfileDTO);

            List<PostDetailWithoutUserDTO> postWithoutUserList = postService.findUserPostList(userId);
            List<PostAllDetailDTO> postAllDetailDTOList = new ArrayList<>();
            for (PostDetailWithoutUserDTO postWithoutUser : postWithoutUserList) {
                Boolean isLiked = postService.findIsLiked(postWithoutUser.getId(), loginUserId);
                postAllDetailDTOList.add(new PostAllDetailDTO(postWithoutUser.getId(), postWithoutUser.getUserId(), postWithoutUser.getUsername(), postWithoutUser.getAvatarId(), postWithoutUser.getDescribe(), postWithoutUser.getVideoId(), postWithoutUser.getUpdateTime(), postWithoutUser.getLikesCount(), isLiked));
            }
            logger.trace("toMe:postList " + postAllDetailDTOList.toString());
            model.addAttribute("postList", postAllDetailDTOList);
            return "user/me";

        } else {
            //其他人的页面返回user/othersProfile
            logger.trace("toOtherUser:" + userId);
            //findIsFollowed
            boolean isFollowed = userService.findIsFollowed(loginUserId, userId);
            model.addAttribute("isFollowed", isFollowed);

            //这个DTO其实不需要里边的 likesCount 但是多需要 userId（用来添加或删除关注）
            ShowProfileDTO showProfileDTO = mainlyUseCase.showProfile(userId);
            logger.trace("toOthersProfile:showProfileDTO " + showProfileDTO.toString());
            model.addAttribute("othersProfileDTO", showProfileDTO);

            List<PostDetailWithoutUserDTO> postWithoutUserList = postService.findUserPostList(userId);
            List<PostAllDetailDTO> postAllDetailDTOList = new ArrayList<>();
            for (PostDetailWithoutUserDTO postWithoutUser : postWithoutUserList) {
                Boolean isLiked = postService.findIsLiked(postWithoutUser.getId(), loginUserId);
                postAllDetailDTOList.add(new PostAllDetailDTO(postWithoutUser.getId(), postWithoutUser.getUserId(), postWithoutUser.getUsername(), postWithoutUser.getAvatarId(), postWithoutUser.getDescribe(), postWithoutUser.getVideoId(), postWithoutUser.getUpdateTime(), postWithoutUser.getLikesCount(), isLiked));
            }
            logger.trace("toOthersProfile:postList " + postAllDetailDTOList.toString());
            model.addAttribute("postList", postAllDetailDTOList);
            return "user/othersProfile";
        }
    }

    @GetMapping("/profile/{id}")
    String toEditProfile(@PathVariable("id") String userId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        logger.trace("toEditProfile:" + userId);

        Profile profile = userService.findProfileByUserId(userId);
        model.addAttribute("profile", profile);
        return "user/editProfile";
    }

    @PutMapping("/profile")
    String updateProfile(Profile profile, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        logger.trace("updateProfile:" + profile.toString());
        userService.saveProfile(profile);
        return "redirect:/user/" + loginUserId;
    }

    @PutMapping("/avatar")
    String updateAvatar(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        //（一个表单对应一个Controller？）Controller接收不了混合参数，怎么把profile和avatarId绑定上？只能用session了吗？
        String loginUserId = (String) session.getAttribute("loginUserId");

        if (file.getSize() != 0) {
            logger.trace("updateAvatar:" + file.toString());
            logger.debug(file.getOriginalFilename());


            userService.updateAvatar(file, loginUserId);
        }
        return "redirect:/user/" + loginUserId;

    }

    @GetMapping("/avatars/stream/{id}")
    public void streamAvatar(@PathVariable("id") String avatarId, HttpServletResponse response) throws Exception {
        InputStream avatarStream = userService.getAvatarStream(avatarId);
        FileCopyUtils.copy(avatarStream, response.getOutputStream());
    }

    @GetMapping("/videos/stream/{id}")
    public void streamVideo(@PathVariable("id") String videoId, HttpServletResponse response) throws Exception {
        InputStream videoStream = userService.getVideoStream(videoId);
        FileCopyUtils.copy(videoStream, response.getOutputStream());
    }

    @DeleteMapping("/post/{id}")
    String deletePost(@PathVariable("id") String postId, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        userService.deletePost(postId);
        return "redirect:/user" + loginUserId;
    }


    @PostMapping("/follow/{id}")
    String followUser(@PathVariable("id") String followingId, HttpSession session) {
        logger.trace("followUser:" + followingId);
        String followerId = (String) session.getAttribute("loginUserId");
        userService.addFollow(followerId, followingId);
        return "redirect:/user/" + followingId;
    }

    @DeleteMapping("/unFollow/{id}")
    String unFollowUser(@PathVariable("id") String followingId, HttpSession session) {
        logger.trace("unFollowUser:" + followingId);
        String followerId = (String) session.getAttribute("loginUserId");
        userService.unFollow(followerId, followingId);
        return "redirect:/user/" + followingId;
    }

    @GetMapping("/user/likeList/{id}")
    String toLikeList(@PathVariable("id") String likedUserId, Model model, HttpSession session) {
        logger.trace("toLikeList:" + likedUserId);
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        List<PostAllDetailDTO> postAllDetailDTOList = postService.findLikePostList(likedUserId);

        model.addAttribute("postList", postAllDetailDTOList);
        return "user/likePostList";
    }
}
