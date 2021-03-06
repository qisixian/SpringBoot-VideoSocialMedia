package com.sishiancode.springboot;

import com.sishiancode.springboot.repository.*;
import com.sishiancode.springboot.service.EditorService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class RandomTest {

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
    private EditorService editorService;

    @Autowired
    private AdministratorRepository administratorRepository;


    @Test
    void randomTest() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(50));
        }
    }

    @Test
    void listTest() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integerList.add(1);
        }
        System.out.println(integerList.size());
    }

    @Test
    void suggestUserTest() {

    }

    @Test
    void arrayListTest() {
//        List<Post> posts = postRepository.findAll();
//        logger.debug(posts.toString());
//        logger.debug("=====================");
//
//        posts.removeIf((post) -> {
//            return post.getUsername().equals("阿龍");
//        });
//
//        //foreach会抛出ConcurrentModificationException异常
//        for (Post post : posts) {
//            if (post.getUsername().equals("阿龍")) {
//
//                logger.debug(post.toString());
//                posts.remove(post);
//            }
//        }
//
//        for (int i = 0; i < posts.size(); i++) {
//            if(posts.get(i).getUsername().equals("阿龍")){
//                logger.debug(posts.get(i).toString());
//                posts.remove(i);
//            }
//        }
//
//        logger.debug("=====================");
//        logger.debug(posts.toString());

    }

}
