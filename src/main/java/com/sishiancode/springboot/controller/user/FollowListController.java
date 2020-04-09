package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FollowListController extends BaseController {
    @GetMapping("user/followingList/{id}")
    String toFollowingList(@PathVariable("id") String followerId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        //id得再查到profile，你让前端去查profile吗
        List<Profile> followingProfileList = userService.findFollowingProfileList(followerId);
        model.addAttribute("profileList", followingProfileList);
        return "user/followList";
    }

    @GetMapping("user/followerList/{id}")
    String toFollowerList(@PathVariable("id") String followingId, Model model, HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        List<Profile> followerProfileList = userService.findFollowerProfileList(followingId);
        model.addAttribute("profileList", followerProfileList);

        return "user/followList";
    }
}
