package com.sishiancode.springboot.service;

import com.sishiancode.springboot.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected PostCommentRepository postCommentRepository;
    @Autowired
    protected PostLikeRepository postLikeRepository;
    @Autowired
    protected ProfileRepository profileRepository;
    @Autowired
    protected UserFollowingRepository userFollowingRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AdministratorRepository administratorRepository;


    @Autowired
    GridFsOperations gridFsOperations;
    @Autowired
    GridFsTemplate gridFsTemplate;

    Logger logger = LoggerFactory.getLogger(getClass());
}
