package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminListController extends BaseController {
    @GetMapping("/admin/adminList")
    String toAdminAdminList(Model model, HttpSession session) {
        logger.trace("toAdminAdminList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator loginAdmin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", loginAdmin);

        List<Administrator> allAdmin = adminService.findAllAdmin();
        model.addAttribute("allAdmin", allAdmin);
        return "admin/adminList";
    }

    @GetMapping("/admin/admin")
    String toAddAdmin(Model model, HttpSession session) {
        logger.trace("toAddAdmin");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator loginAdmin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", loginAdmin);

        return "admin/addAdmin";
    }

    @GetMapping("/admin/admin/{id}")
    String toEditAdmin(@PathVariable("id") String adminId, Model model, HttpSession session) {
        logger.trace("toEditAdmin:" + adminId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator loginAdmin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", loginAdmin);

        Administrator admin = adminService.findAdminById(adminId);
        model.addAttribute("admin", admin);
        return "admin/addAdmin";
    }

    @PostMapping("/admin/admin")
    String addAdmin(Administrator admin) {
        logger.trace("addAdmin:" + admin.toString());
        if (adminService.saveAdmin(admin) != null) {
            return "redirect:/admin/adminList";
        } else {
            return "redirect:/admin/admin";
        }
    }

    @PutMapping("/admin/admin")
    String updateAdmin(Administrator admin) {
        logger.trace("updateAdmin:" + admin.toString());
        if (adminService.saveAdmin(admin) != null) {
            return "redirect:/admin/adminList";
        } else {
            return "redirect:/admin/admin/" + admin.getId();
        }
    }

    @DeleteMapping("/admin/admin/{id}")
    String deleteAdmin(@PathVariable("id") String adminId) {
        logger.trace("deleteAdmin:" + adminId);
        adminService.deleteAdmin(adminId);
        return "redirect:/admin/adminList";
    }
}
