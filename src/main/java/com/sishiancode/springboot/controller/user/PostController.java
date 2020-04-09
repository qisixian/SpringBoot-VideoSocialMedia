package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.PostAllDetailDTO;
import com.sishiancode.springboot.dto.PostCommentDTO;
import com.sishiancode.springboot.dto.PostDetailWithoutUserDTO;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController extends BaseController {
    @GetMapping("/post/{id}")
    String toPost(@PathVariable("id") String postId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        PostDetailWithoutUserDTO postWithoutUser = postService.findPost(postId);
        Boolean isLiked = postService.findIsLiked(postId, loginUserId);
        PostAllDetailDTO postAllDetailDTO = new PostAllDetailDTO(postWithoutUser.getId(), postWithoutUser.getUserId(), postWithoutUser.getUsername(), postWithoutUser.getAvatarId(), postWithoutUser.getDescribe(), postWithoutUser.getVideoId(), postWithoutUser.getUpdateTime(), postWithoutUser.getLikesCount(), isLiked);
        model.addAttribute("postAllDetailDTO", postAllDetailDTO);
        List<PostCommentDTO> postCommentDTOList = postService.findPostComment(postId);
        model.addAttribute("postCommentDTOList", postCommentDTOList);
        return "user/post";
    }

    @PostMapping("/post/like/{id}")
    String likePost(@PathVariable("id") String postId, HttpSession session) {
        logger.trace("likePost:" + postId);
        String likedUserId = (String) session.getAttribute("loginUserId");
        postService.addLike(postId, likedUserId);
        return "redirect:/post/" + postId;
    }

    @DeleteMapping("/post/like/{id}")
    String unLikePost(@PathVariable("id") String postId, HttpSession session) {
        logger.trace("unLikePost:" + postId);
        String likedUserId = (String) session.getAttribute("loginUserId");
        postService.unLike(postId, likedUserId);
        return "redirect:/post/" + postId;
    }


    @GetMapping("/post/like/{id}")
    String toLikeList(@PathVariable("id") String postId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        logger.trace("toLikeList:" + postId);

        List<Profile> likedUserProfileList = postService.findPostLikedUserProfile(postId);

        model.addAttribute("profileList", likedUserProfileList);

        return "user/likeUserList";

    }

    @PostMapping("/post/comment/{id}")
    String commentPost(@PathVariable("id") String postId, @RequestParam("receiverName") String receiverName, @RequestParam("comment") String comment, HttpSession session) {
        logger.trace("commentPost:" + postId + " " + receiverName + " " + comment);
        String senderId = (String) session.getAttribute("loginUserId");
        if (receiverName.isEmpty()) {
            postService.addComment(postId, senderId, comment);
        } else {
            postService.addComment(postId, senderId, receiverName, comment);
        }
        return "redirect:/post/" + postId;
    }

    @DeleteMapping("/post/comment/{id}")
    String deleteCommentPost(@PathVariable("id") String postCommentId, @RequestParam("postId") String postId, HttpSession session) {
        logger.trace("deleteCommentPost:" + postCommentId + " " + postId);
        postService.deleteComment(postCommentId);
        return "redirect:/post/" + postId;
    }

}
