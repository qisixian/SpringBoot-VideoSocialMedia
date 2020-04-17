package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.admin.AdminUserFollowingDTO;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.UserFollowing;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserFollowingListController extends BaseController {
    @GetMapping("/admin/userFollowingList")
    String toAdminUserFollowingList(Model model, HttpSession session) {
        logger.trace("toAdminUserFollowingList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        List<AdminUserFollowingDTO> allUserFollowingDTO = adminService.findAllUserFollowingDTO();
        model.addAttribute("allUserFollowingDTO", allUserFollowingDTO);
        return "admin/userFollowingList";
    }

    @GetMapping("/admin/userFollowing")
    String toAddUserFollowing(Model model, HttpSession session) {
        logger.trace("toAddUserFollowing");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        return "admin/addUserFollowing";
    }

    @GetMapping("/admin/userFollowing/{id}")
    String toEditUserFollowing(@PathVariable("id") String userFollowingId, Model model, HttpSession session) {
        logger.trace("toEditUserFollowing:" + userFollowingId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        UserFollowing userFollowing = adminService.findUserFollowingById(userFollowingId);
        model.addAttribute("userFollowing", userFollowing);
        return "admin/addUserFollowing";
    }

    @PostMapping("/admin/userFollowing")
    String addUserFollowing(UserFollowing userFollowing) {
        logger.trace("addUserFollowing:" + userFollowing.toString());
        userFollowing.setLocalDateTime(LocalDateTime.now());
        adminService.saveUserFollowing(userFollowing);
        return "redirect:/admin/userFollowingList";
    }

    @PutMapping("/admin/userFollowing")
    String updateUserFollowing(UserFollowing userFollowing) {
        logger.trace("updateUserFollowing:" + userFollowing.toString());
        adminService.saveUserFollowing(userFollowing);
        return "redirect:/admin/userFollowingList";
    }

    @DeleteMapping("/admin/userFollowing/{id}")
    String deleteUserFollowing(@PathVariable("id") String userFollowingId) {
        logger.trace("deleteUserFollowing:" + userFollowingId);
        adminService.deleteUserFollowing(userFollowingId);
        return "redirect:/admin/userFollowingList";
    }
}
