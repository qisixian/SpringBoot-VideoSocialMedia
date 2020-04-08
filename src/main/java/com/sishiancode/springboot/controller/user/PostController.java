package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.PostCommentDTO;
import com.sishiancode.springboot.entities.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostController extends BaseController {
    @GetMapping("/post/{id}")
    String toPost(@PathVariable("id") String postId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        Post post = postService.findPost(postId);
        model.addAttribute("post", post);
        List<PostCommentDTO> postCommentDTOList = postService.findPostComment(postId);
        model.addAttribute("postCommentDTOList", postCommentDTOList);
        Integer likesCount = postService.findPostLikesCount(postId);
        model.addAttribute("likesCount", likesCount);
        return "user/post";
    }
//    @PostMapping("/post/like/{id}")
//    String likePost(@PathVariable("id") String postId, HttpSession session) {
//        logger.trace("likePost:" + postId);
//        String followerId = (String) session.getAttribute("loginUserId");
//        userService.addFollow(followerId, followingId);
//        return "redirect:/user/"+followingId;
//    }
//    @DeleteMapping("/post/like/{id}")
//    String unLikePost(@PathVariable("id") String postId, HttpSession session) {
//        logger.trace("unLikePost:" + postId);
//        String followerId = (String) session.getAttribute("loginUserId");
//        userService.unFollow(followerId, followingId);
//        return "redirect:/post/"+postId;
//    }
}
