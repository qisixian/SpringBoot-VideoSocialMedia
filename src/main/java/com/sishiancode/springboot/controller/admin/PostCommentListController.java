package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.dto.admin.AdminPostCommentDTO;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.PostComment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PostCommentListController extends BaseController {
    @GetMapping("/admin/postCommentList")
    String toAdminPostCommentList(Model model, HttpSession session) {
        logger.trace("toAdminPostCommentList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        List<AdminPostCommentDTO> allPostCommentDTO = adminService.findAllPostCommentDTO();
        model.addAttribute("allPostCommentDTO", allPostCommentDTO);
        return "admin/postCommentList";
    }

    @GetMapping("/admin/postComment")
    String toAddPostComment(Model model, HttpSession session) {
        logger.trace("toAddPostComment");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        return "admin/addPostComment";
    }

    @GetMapping("/admin/postComment/{id}")
    String toEditPostComment(@PathVariable("id") String postCommentId, Model model, HttpSession session) {
        logger.trace("toEditPostComment:" + postCommentId);
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        PostComment postComment = adminService.findPostCommentById(postCommentId);
        model.addAttribute("postComment", postComment);
        return "admin/addPostComment";
    }

    @PostMapping("/admin/postComment")
    String addPostComment(PostComment postComment, Model model, HttpSession session) {
        logger.trace("addPostComment:" + postComment.toString());

        if (adminService.savePostComment(postComment) != null) {
            logger.trace("addPostComment:OK");
            return "redirect:/admin/postCommentList";
        } else {
            logger.trace("addPostComment:false");
//            //返回管理员账号信息
//            String loginAdminId = (String) session.getAttribute("loginAdminId");
//            Administrator admin = adminService.findAdminById(loginAdminId);
//            model.addAttribute("loginAdmin", admin);
//            return "admin/addPostComment";
            return "redirect:/admin/postComment";
        }
    }

    @PutMapping("/admin/postComment")
    String updatePostComment(PostComment postComment, Model model, HttpSession session) {
        logger.trace("updatePostComment:" + postComment.toString());
        if (adminService.savePostComment(postComment) != null) {
            return "redirect:/admin/postCommentList";
        } else {
            //返回管理员账号信息
            String loginAdminId = (String) session.getAttribute("loginAdminId");
            Administrator admin = adminService.findAdminById(loginAdminId);
            model.addAttribute("loginAdmin", admin);
            model.addAttribute("postComment", postComment);
            return "admin/addPostComment";
        }
    }

    @DeleteMapping("/admin/postComment/{id}")
    String deletePostComment(@PathVariable("id") String postCommentId) {
        logger.trace("deletePostComment:" + postCommentId);
        adminService.deletePostComment(postCommentId);
        return "redirect:/admin/postCommentList";
    }
}
