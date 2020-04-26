package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.admin.AdminPostLikeDTO;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.PostLike;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostLikeListController extends BaseController {
    @GetMapping("/admin/postLikeList")
    String toAdminPostLikeList(Model model, HttpSession session) {
        logger.trace("toAdminPostLikeList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        List<AdminPostLikeDTO> allPostLikeDTO = adminService.findAllPostLikeDTO();
        model.addAttribute("allPostLikeDTO", allPostLikeDTO);
        return "admin/postLikeList";
    }

    @GetMapping("/admin/postLike")
    String toAddPostLike(Model model, HttpSession session) {
        logger.trace("toAddPostLike");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        return "admin/addPostLike";
    }

    @GetMapping("/admin/postLike/{id}")
    String toEditPostLike(@PathVariable("id") String postLikeId, Model model, HttpSession session) {
        logger.trace("toEditPostLike:" + postLikeId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        PostLike postLike = adminService.findPostLikeById(postLikeId);
        model.addAttribute("postLike", postLike);
        return "admin/addPostLike";
    }

    @PostMapping("/admin/postLike")
    String addPostLike(PostLike postLike) {
        logger.trace("addPostLike:" + postLike.toString());
        if (adminService.savePostLike(postLike) != null) {
            return "redirect:/admin/postLikeList";
        } else {
            return "redirect:/admin/postLike";
        }
    }

    @PutMapping("/admin/postLike")
    String updatePostLike(PostLike postLike) {
        logger.trace("updatePostLike:" + postLike.toString());
        postLike.setLocalDateTime(LocalDateTime.now());
        adminService.savePostLike(postLike);
        return "redirect:/admin/postLikeList";
    }

    @DeleteMapping("/admin/postLike/{id}")
    String deletePostLike(@PathVariable("id") String postLikeId) {
        logger.trace("deletePostLike:" + postLikeId);
        adminService.deletePostLike(postLikeId);
        return "redirect:/admin/postLikeList";
    }
}
