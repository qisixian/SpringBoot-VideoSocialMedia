package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostListController extends BaseController {

    @GetMapping("/admin/postList")
    String toAdminPostList(Model model, HttpSession session) {
        logger.trace("Admin/PostListController-toAdminPostList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("admin", admin);

        List<Post> allPost = adminService.findAllPost();
        model.addAttribute("allPost", allPost);
        return "admin/postList";
    }

    @DeleteMapping("/admin/post/{id}")
    String deletePost(@PathVariable("id") String postId) {
        logger.trace("Admin/PostListController-deletePost:" + postId);
        adminService.deletePost(postId);
        return "redirect:/admin/postList";
    }
}
