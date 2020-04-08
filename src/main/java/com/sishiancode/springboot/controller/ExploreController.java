package com.sishiancode.springboot.controller;

import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ExploreController extends BaseController {
    @GetMapping({"/", "/explore"})
    String toExplore(Model model, HttpSession session) {
        //这个方法不被Interceptor墙，session尝试取用户ID
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);

        List<Profile> allProfile = adminService.findAllProfile();
        model.addAttribute("allProfile", allProfile);
        return "user/explore";
    }
}
