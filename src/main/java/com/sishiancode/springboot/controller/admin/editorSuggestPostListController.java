package com.sishiancode.springboot.controller.admin;

import com.sishiancode.springboot.controller.BaseController;
import com.sishiancode.springboot.entities.Administrator;
import com.sishiancode.springboot.entities.EditorSuggestPost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class editorSuggestPostListController extends BaseController {
    @GetMapping("/admin/editorSuggestPostList")
    String toAdminEditorSuggestPostList(Model model, HttpSession session) {
        logger.trace("toAdminEditorSuggestPostList");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        List<EditorSuggestPost> allEditorSuggestPost = adminService.findAllEditorSuggestPost();
        model.addAttribute("allEditorSuggestPost", allEditorSuggestPost);
        return "admin/editorSuggestPostList";
    }

    @GetMapping("/admin/editorSuggestPost")
    String toAddEditorSuggestPost(Model model, HttpSession session) {
        logger.trace("toAddEditorSuggestPost");
        //返回管理员账号信息
        String loginAdminId = (String) session.getAttribute("loginAdminId");
        Administrator admin = adminService.findAdminById(loginAdminId);
        model.addAttribute("loginAdmin", admin);

        return "admin/addEditorSuggestPost";
    }

    @PostMapping("/admin/editorSuggestPost")
    String addEditorSuggestPost(EditorSuggestPost editorSuggestPost, Model model, HttpSession session) {
        logger.trace("addEditorSuggestPost:" + editorSuggestPost.toString());

        if (adminService.saveEditorSuggestPost(editorSuggestPost) != null) {
            logger.trace("addEditorSuggestPost:OK");
            return "redirect:/admin/editorSuggestPostList";
        } else {
            logger.trace("addEditorSuggestPost:false");
            return "redirect:/admin/addEditorSuggestPost";
        }
    }

    @DeleteMapping("/admin/editorSuggestPost/{id}")
    String deleteEditorSuggestPostList(@PathVariable("id") String editorSuggestPostId) {
        logger.trace("deleteEditorSuggestPostList:" + editorSuggestPostId);
        adminService.deleteEditorSuggestPost(editorSuggestPostId);
        return "redirect:/admin/editorSuggestPostList";
    }
}
