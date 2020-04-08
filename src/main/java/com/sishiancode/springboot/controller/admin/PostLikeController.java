package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.PostLike;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostLikeController extends BaseController {
    @GetMapping("/admin/postLikeList")
    String toAdminPostLikeList(Model model, HttpSession session) {
        logger.trace("Admin/PostLikeListController-toAdminPostLikeList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        List<PostLike> allPostLike = adminService.findAllPostLike();
        model.addAttribute("allPostLike", allPostLike);
        return "admin/postLikeList";
    }

    @GetMapping("/admin/postLike")
    String toAddPostLike(Model model, HttpSession session) {
        logger.trace("Admin/PostLikeListController-toAddPostLike");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        return "admin/addPostLike";
    }

    @GetMapping("/admin/postLike/{id}")
    String toEditPostLike(@PathVariable("id") String postLikeId, Model model, HttpSession session) {
        logger.trace("Admin/PostLikeListController-toEditPostLike:" + postLikeId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        PostLike postLike = adminService.findPostLikeById(postLikeId);
        model.addAttribute("postLike", postLike);
        return "admin/addPostLike";
    }

    @PostMapping("/admin/postLike")
    String addPostLike(PostLike postLike) {
        logger.trace("Admin/PostLikeListController-addPostLike:" + postLike.toString());
        postLike.setLocalDateTime(LocalDateTime.now());
        adminService.savePostLike(postLike);
        return "redirect:/admin/postLikeList";
    }

    @PutMapping("/admin/postLike")
    String updatePostLike(PostLike postLike) {
        logger.trace("Admin/PostLikeListController-updateUser:" + postLike.toString());
        postLike.setLocalDateTime(LocalDateTime.now());
        adminService.savePostLike(postLike);
        return "redirect:/admin/postLikeList";
    }

    @DeleteMapping("/admin/postLike/{id}")
    String deletePostLike(@PathVariable("id") String postLikeId) {
        logger.trace("Admin/PostLikeListController-deletePostLike:" + postLikeId);
        adminService.deletePostLike(postLikeId);
        return "redirect:/admin/postLikeList";
    }
}
