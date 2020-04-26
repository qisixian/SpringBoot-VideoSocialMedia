package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserListController extends BaseController {
    @GetMapping("/admin/userList")
    String toAdminUserList(Model model, HttpSession session) {
        logger.trace("toAdminUserList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        List<User> allUser = adminService.findAllUser();
        model.addAttribute("allUser", allUser);
        return "admin/userList";
    }

    @GetMapping("/admin/user")
    String toAddUser(Model model, HttpSession session) {
        logger.trace("toAddUser");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        return "admin/addUser";
    }

    @GetMapping("/admin/user/{id}")
    String toEditUser(@PathVariable("id") String userId, Model model, HttpSession session) {
        logger.trace("toEditUser:" + userId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        User user = adminService.findUserById(userId);
        model.addAttribute("user", user);
        return "admin/addUser";
    }

    @PostMapping("/admin/user")
    String addUser(User user) {
        logger.trace("addUser:" + user.toString());
        if (adminService.saveUser(user) != null) {
            return "redirect:/admin/userList";
        } else {
            return "redirect:/admin/user";
        }
    }

    @PutMapping("/admin/user")
    String updateUser(User user) {
        logger.trace("updateUser:" + user.toString());
        if (adminService.saveUser(user) != null) {
            return "redirect:/admin/userList";
        } else {
            return "redirect:/admin/user/" + user.getId();
        }
    }

    @DeleteMapping("/admin/user/{id}")
    String deleteUser(@PathVariable("id") String userId) {
        logger.trace("deleteUser:" + userId);
        adminService.deleteUser(userId);
        return "redirect:/admin/userList";
    }
}
