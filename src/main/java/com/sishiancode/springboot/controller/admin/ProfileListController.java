package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileListController extends BaseController {
    @GetMapping("/admin/profileList")
    String toAdminProfileList(Model model, HttpSession session) {
        logger.trace("toAdminProfileList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);
        //返回profile列表
        List<Profile> allProfile = adminService.findAllProfile();
        model.addAttribute("allProfile", allProfile);
        return "admin/profileList";
    }

    @GetMapping("/admin/profile/{id}")
    String toEditProfile(@PathVariable("id") String profileId, Model model, HttpSession session) {
        logger.trace("toEditProfile:" + profileId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        Profile profile = adminService.findProfileById(profileId);
        model.addAttribute("profile", profile);
        return "admin/addProfile";
    }

    @PutMapping("/admin/profile")
    String updateProfile(Profile profile) {
        logger.trace("updateProfile:" + profile.toString());
        adminService.saveProfile(profile);
        return "redirect:/admin/profileList";
    }
}
