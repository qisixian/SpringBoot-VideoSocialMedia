package com.sishiancode.springboot.controller.user;

import com.sishiancode.springboot.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class UploadController extends BaseController {
    @GetMapping("/upload")
    public String toUpload(Model model, HttpSession session) {
        logger.trace("toUpload:");
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
        return "user/upload";
    }

    @PostMapping("/upload")
    public String uploadPost(@RequestParam("file") MultipartFile file, @RequestParam("describe") String describe, Model model, HttpSession session) throws IOException {
        String loginUserId = (String) session.getAttribute("loginUserId");
        model.addAttribute("loginUserId", loginUserId);
//        file.
        if (file.getSize() != 0) {
            logger.trace("uploadPost:" + file.toString());
            logger.debug(file.getOriginalFilename());

            //得多建几个DTO可能，这样Controller是直接依赖service的
            //乱在哪了？Controller接收数据的方式不干净吗？
            userService.addPost(loginUserId, describe, file);
        }
        return "redirect:/user/" + loginUserId;
    }
}
