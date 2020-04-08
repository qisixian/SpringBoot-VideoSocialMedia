package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.PostWithProfileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController extends BaseController {


    @GetMapping("/home")
    String toHome(Model model, HttpSession session) {
        //LoginHandlerInterceptor检查过了，session得到用户ID
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        List<PostWithProfileDTO> followingPostWithProfiles = mainlyUseCase.showFollowingPost(loginUserId);
        logger.trace("HomeController-toHome:" + followingPostWithProfiles.toString());
        model.addAttribute("followingPostWithProfiles", followingPostWithProfiles);
        return "user/home";

    }
}
