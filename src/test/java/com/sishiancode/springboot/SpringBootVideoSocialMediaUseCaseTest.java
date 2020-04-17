package com.sishiancode.springboot;

import com.sishiancode.springboot.repository.*;
import com.sishiancode.springboot.service.AdminService;
import com.sishiancode.springboot.service.LoginService;
import com.sishiancode.springboot.service.PostService;
import com.sishiancode.springboot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@SpringBootTest
public class SpringBootVideoSocialMediaUseCaseTest {
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostCommentRepository postCommentRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserFollowingRepository userFollowingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private GridFsTemplate gridFsTemplate;

//    @Test
//    void showFollowingPostTest() {
//
////        List<Post> followingPost = mainlyUseCase.showFollowingPost("5e80696a1e740b7140bf4418");
//
//
////        logger.trace(followingPost.toString());
//    }
//
//    @Test
//    void postCountTest() {
//        Integer count = mainlyUseCase.postsCount("5e80696a1e740b7140bf4418");
//        logger.debug(count.toString());
//
//    }
//
//    @Test
//    void profileDTOTest() {
//        ShowProfileDTO showProfileDTO = mainlyUseCase.showProfile("5e80696a1e740b7140bf4418");
//        logger.debug(showProfileDTO.toString());
////        Integer count = profileRepository.countByUserId("5e80696a1e740b7140bf4418");
////        logger.debug(count.toString());
//    }
//
//    @Test
//    void loginTest() {
////        AdministratorDTO administratorDTO = administratorRepository.findByPhoneNumberAndPassword("15811465067", "123456");
////        if (administratorDTO==null){
////            logger.debug("null");
////        }else {
////            logger.debug(administratorDTO.toString());
////        }
//        logger.debug(loginService.LoginAdmin("15811465067", "123456"));
//    }
//
//    @Test
//    void adminServiceTest() {
//        logger.debug(adminService.findAdminById("5e88c218f418052970dd7335").toString());
//    }
//
//    @Test
//    void adminUserTest() {
//        List<User> admin = userRepository.findAll();
//        logger.debug(admin.toString());
//    }
//
//    @Test
//    void deleteFile() {
//        gridFsTemplate.delete(new Query(Criteria.where("_id").is("5e8627431ed71f031e848127")));
//    }
//
//    @Test
//    void findByUserIdAndFollowingIdTest() {
//        UserFollowing userFollowing = userFollowingRepository.findByUserIdAndFollowingId("234", "234");
//        if (userFollowing == null) {
//            logger.debug("isnull");
//        } else {
//            logger.debug(userFollowing.toString());
//        }
//    }
//
//    @Test
//    void findFollowingListProfileTest() {
//        userService.findFollowingProfileList("5e8c8d0ab7c29f7515e21e83");
//    }
//
//    @Test
//    void showFollowingPostTest2() {
//        postService.findFollowingPost("5e8c8d0ab7c29f7515e21e83");
//    }
//
//    @Test
//    void findPostCommentTest() {
////        postService.findPostComment();
//    }
}
