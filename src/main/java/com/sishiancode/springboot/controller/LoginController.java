package com.sishiancode.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginController extends BaseController {
    @GetMapping("/login")
    String toUserLogin(HttpSession session) {
        logger.trace("toUserLogin");
        //这个方法不被Interceptor墙，session尝试取用户ID
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            //没有返回login
            return "login";
        } else {
            //有返回user页面
            return "redirect:/user/" + loginUserId;
        }
    }

    @PostMapping("/login")
    String userLogin(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        logger.trace("userLogin:" + phoneNumber + " " + password);
        if (phoneNumber.isEmpty() || password.isEmpty()) {
            //登录失败
            logger.trace("登录失败");
            map.put("msg", "请填写用户名和密码");
            return "login";
        } else {
            String loginUserId = loginService.LoginUser(phoneNumber, password);
            if (loginUserId != null) {
                //用户登录成功
                logger.trace("用户登录成功");
                session.setAttribute("loginUserId", loginUserId);
                return "redirect:/home";
            } else {
                //登录失败
                logger.trace("登录失败");
                map.put("msg", "用户名密码错误");
                return "login";
            }
        }
    }

    @PostMapping("/userLogout")
    String userLogout(HttpSession session) {
        logger.trace("userLogout:" + session.getAttribute("loginUserId"));

        session.removeAttribute("loginUserId");
        return "redirect:/";
    }

    @GetMapping("/adminLogin")
    String toAdminLogin() {
        logger.trace("toAdminLogin");
        return "adminLogin";
    }

    @PostMapping("/adminLogin")
    String adminLogin(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) {
        logger.trace("adminLogin:" + phoneNumber + " " + password);
        String loginAdminId = loginService.LoginAdmin(phoneNumber, password);
        //这段逻辑如果loginAdminId是空值的话有问题，不能排查出所有错误
        if (loginAdminId != "null") {
            //管理员登录成功
            logger.trace("管理员登录成功");
            session.setAttribute("loginAdminId", loginAdminId);
            return "redirect:/admin/userList";
        } else {
            //登录失败
            logger.trace("登录失败");
            map.put("msg", "用户名密码错误");
            return "adminLogin";
        }
    }

    @PostMapping("/adminLogout")
    String adminLogout(HttpSession session) {
        logger.trace("adminLogout:" + session.getAttribute("loginAdminId"));
        session.removeAttribute("loginAdminId");
        return "redirect:/adminLogin";
    }

    @GetMapping("/signUp")
    String toSignUp(HttpSession session) {
        //这个方法不被Interceptor墙，session尝试取用户ID
        logger.trace("toSignUp");
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            //没有返回login
            return "signUp";
        } else {
            //有返回user页面
            return "redirect:/user/" + loginUserId;
        }
    }

    @PostMapping("/signUp")
    String userSignUp(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("username") String username, @RequestParam("password") String password, Map<String, Object> map, HttpSession session) throws IOException {
        logger.trace("userSignUp:" + phoneNumber + " " + username + " " + password);
        String SignUpUserId = loginService.SignUpUser(phoneNumber, username, password);
        //这段逻辑如果SignUpUserId是空值的话有问题，不能排查出所有错误
        if (SignUpUserId != "null") {
            //用户注册成功
            logger.trace("用户注册成功");
            session.setAttribute("loginUserId", SignUpUserId);
            return "redirect:/home";
        } else {
            //用户已存在
            logger.trace("手机号已注册");
            map.put("msg", "这个手机号已经注册过了");
            return "signUp";
        }
    }
}
