package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.CommentDetailDTO;
import com.sishiancode.springboot.dto.FollowerDetailDTO;
import com.sishiancode.springboot.dto.LikedDetailDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NewsController extends BaseController {
    @GetMapping("/news/comment")
    String toNewComments(Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        List<CommentDetailDTO> commentDetailList = newsService.findAllComment(loginUserId);
        logger.trace("toNewComments:" + commentDetailList.toString());
        model.addAttribute("commentDetailList", commentDetailList);
        return "news/newCommentList";
    }

    @GetMapping("/news/like")
    String toNewLikes(Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        List<LikedDetailDTO> likedDetailList = newsService.findAllLikes(loginUserId);
        logger.trace("toNewLikes:" + likedDetailList.toString());
        model.addAttribute("likedDetailList", likedDetailList);
        return "news/newLikeList";
    }

    @GetMapping("/news/follower")
    String toNewFollowers(Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        List<FollowerDetailDTO> followerDetailList = newsService.findAllFollower(loginUserId);
        logger.trace("toNewFollowers:" + followerDetailList.toString());
        model.addAttribute("followerDetailList", followerDetailList);
        return "news/newFollowerList";
    }
}
