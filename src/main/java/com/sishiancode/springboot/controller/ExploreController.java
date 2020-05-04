package com.sishiancode.springboot.controller;

import com.sishiancode.springboot.dto.PostAllDetailDTO;
import com.sishiancode.springboot.dto.PostDetailWithoutUserDTO;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ExploreController extends BaseController {
    @GetMapping({"/", "/explore"})
    String toExplore(Model model, HttpSession session) {
        //这个方法不被Interceptor墙，session尝试取用户ID
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        //suggestPostList
        List<PostDetailWithoutUserDTO> postWithoutUserList = postService.suggestPost();
        List<PostAllDetailDTO> postAllDetailDTOList = new ArrayList<>();
        if (loginUserId != null) {
            for (PostDetailWithoutUserDTO postWithoutUser : postWithoutUserList) {
                Boolean isLiked = postService.findIsLiked(postWithoutUser.getId(), loginUserId);
                postAllDetailDTOList.add(new PostAllDetailDTO(postWithoutUser.getId(), postWithoutUser.getUserId(), postWithoutUser.getUsername(), postWithoutUser.getAvatarId(), postWithoutUser.getDescribe(), postWithoutUser.getVideoId(), postWithoutUser.getUpdateTime(), postWithoutUser.getLikesCount(), isLiked));
            }
        } else {
            for (PostDetailWithoutUserDTO postWithoutUser : postWithoutUserList) {
                Boolean isLiked = false;
                postAllDetailDTOList.add(new PostAllDetailDTO(postWithoutUser.getId(), postWithoutUser.getUserId(), postWithoutUser.getUsername(), postWithoutUser.getAvatarId(), postWithoutUser.getDescribe(), postWithoutUser.getVideoId(), postWithoutUser.getUpdateTime(), postWithoutUser.getLikesCount(), isLiked));
            }
        }
        model.addAttribute("suggestPostList", postAllDetailDTOList);


        List<Profile> allProfile = adminService.findAllProfile();
        model.addAttribute("suggestUserList", allProfile);
        return "user/explore";
    }
}
