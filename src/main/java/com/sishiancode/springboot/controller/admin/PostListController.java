package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PostListController extends BaseController {

    @GetMapping("/admin/postList")
    String toAdminPostList(Model model, HttpSession session) {
        logger.trace("toAdminPostList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        List<Post> allPost = adminService.findAllPost();
        model.addAttribute("allPost", allPost);
        return "admin/postList";
    }

    @GetMapping("/admin/post/{id}")
    String toEditPost(@PathVariable("id") String postId, Model model, HttpSession session) {
        logger.trace("toEditPost:" + postId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        Post post = adminService.findPostById(postId);
        model.addAttribute("post", post);
        return "admin/editPost";
    }

    @PutMapping("/admin/post")
    String updatePost(Post post) {
        logger.trace("updatePost:" + post.toString());
        post.setUpdateTime(LocalDateTime.now());
        adminService.savePost(post);
        return "redirect:/admin/postList";
    }

    @DeleteMapping("/admin/post/{id}")
    String deletePost(@PathVariable("id") String postId) {
        logger.trace("deletePost:" + postId);
        adminService.deletePost(postId);
        return "redirect:/admin/postList";
    }
}
