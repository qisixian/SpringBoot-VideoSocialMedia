package com.sishiancode.springboot.controller;

import com.sishiancode.springboot.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    @Autowired
    protected LoginService loginService;
    @Autowired
    protected AdminService adminService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected PostService postService;
    @Autowired
    protected NewsService newsService;
    @Autowired
    protected EditorService editorService;

    protected Logger logger = LoggerFactory.getLogger(getClass());
}
