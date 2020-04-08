package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import org.springframework.stereotype.Controller;

@Controller
public class OthersProfileController extends BaseController {
//    @GetMapping("/othersProfile/{id}")
//    String toOthersProfile(@PathVariable("id") String otherUserId, Model model, HttpSession session) {
//        String followerId = (String) session.getAttribute("loginUserId");
//        //如果是来到自己的页面返回me
//        if (followerId.equals(otherUserId)) {
//            return "redirect:/me";
//        }
//        else {
//            //findIsFollowed
//            boolean isFollowed = userService.findIsFollowed(followerId, otherUserId);
//            model.addAttribute("isFollowed", isFollowed);
//            logger.trace("toOthersProfile:" + otherUserId);
//            //这个DTO其实不需要里边的 likesCount 但是多需要 userId（用来添加或删除关注）
//            ShowProfileDTO showProfileDTO = mainlyUseCase.showProfile(otherUserId);
//            List<Post> othersPostList = mainlyUseCase.showUserPostList(otherUserId);
//            logger.trace("toOthersProfile:showProfileDTO " + showProfileDTO.toString());
//            logger.trace("toOthersProfile:otherPostList " + othersPostList.toString());
//            model.addAttribute("othersProfileDTO", showProfileDTO);
//            model.addAttribute("othersPostList", othersPostList);
//            return "user/othersProfile";
//        }
//    }


}
