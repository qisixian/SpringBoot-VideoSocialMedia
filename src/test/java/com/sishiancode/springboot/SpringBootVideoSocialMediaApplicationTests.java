package com.sishiancode.springboot;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.sishiancode.springboot.entities.*;
import com.sishiancode.springboot.repository.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringBootVideoSocialMediaApplicationTests {

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

    @Test
    void contextLoads() {

        User user = new User();
        user.setUsername("bob");
        user.setPassword("123456");
        System.out.println(user);
    }

    @Test
    void mongoDbUserAddTest() {
        List<User> userList = new ArrayList<>();
        User user1 = new User("sishian1", "123456", "15811465068");
        User user2 = new User("sishian2", "123456", "13366076618");
        User user3 = new User("sishian3", "123456", "13241991196");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        logger.debug(userList.toString());
        userRepository.saveAll(userList);
    }

    @Test
    void adminServiceUserAddTest() {
        User user1 = new User("sishian1", "123456", "15811465068");

        logger.debug(userRepository.save(user1).getId());
    }

    @Test
    void mongoDbAdministratorAddTest() {
        Administrator administrator = new Administrator("root", "123456", "15811465067");
//
//        logger.debug(administrator.toString());
        administratorRepository.save(administrator);
    }

    @Test
    void mongoDbProfileAddTest() {
        List<Profile> profileList = new ArrayList<>();
        LocalDate localDate = LocalDate.of(1998, 7, 24);
        logger.debug(localDate.toString());
//        Profile profile1 = new Profile("5e88bd411cd5c329079bf32a","sishian1","15811465068","2235127438@qq.com","5e862ce17f32eb6d24dda5ad","male", LocalDate.of(1998,7,24),"123123123");
        Profile profile1 = new Profile("5e88bd411cd5c329079bf32a", "sishian1", "15811465068", "2235127438@qq.com", "5e862ce17f32eb6d24dda5ad", "male", "123123123");
        Profile profile2 = new Profile("5e88bd411cd5c329079bf32b", "sishian2", "13366076618", "2235127438@qq.com", "5e862cff4d3997486da0e476", "male", "123123123");
        Profile profile3 = new Profile("5e88bd411cd5c329079bf32c", "sishian3", "13241991196", "2235127438@qq.com", "5e862d19e41022354d913b4d", "male", "123123123");
        profileList.add(profile1);
        profileList.add(profile2);
        profileList.add(profile3);
        logger.debug(profileList.toString());
        profileRepository.saveAll(profileList);

    }

    @Test
    void mongoUserFollowingAddTest() {
        List<UserFollowing> userFollowingList = new ArrayList<>();
        UserFollowing userFollowing1 = new UserFollowing("5e80696a1e740b7140bf4418", "5e80696a1e740b7140bf4419", LocalDateTime.now());
        UserFollowing userFollowing2 = new UserFollowing("5e80696a1e740b7140bf4418", "5e80696a1e740b7140bf441a", LocalDateTime.now());
        userFollowingList.add(userFollowing1);
        userFollowingList.add(userFollowing2);
        logger.debug(userFollowingList.toString());
        logger.debug(String.valueOf(userFollowingList.size()));
        userFollowingRepository.saveAll(userFollowingList);

    }

    @Test
    void mongoDbPostAddTest() {
        List<Post> postList = new ArrayList<>();
        Post post1 = new Post("5e80696a1e740b7140bf4418", "sishian1", "post1", "fewljfewiowjejkl", LocalDateTime.now());
        Post post2 = new Post("5e80696a1e740b7140bf4419", "sishian2", "post2", "fewljfewiowjejkl", LocalDateTime.now());
        Post post3 = new Post("5e80696a1e740b7140bf441a", "sishian3", "post3", "fewljfewiowjejkl", LocalDateTime.now());
        Post post4 = new Post("5e80696a1e740b7140bf4418", "sishian1", "post4", "fewljfewiowjejkl", LocalDateTime.now());
        Post post5 = new Post("5e80696a1e740b7140bf4419", "sishian2", "post5", "fewljfewiowjejkl", LocalDateTime.now());
        Post post6 = new Post("5e80696a1e740b7140bf441a", "sishian3", "post6", "fewljfewiowjejkl", LocalDateTime.now());
        Post post7 = new Post("5e80696a1e740b7140bf4418", "sishian1", "post7", "fewljfewiowjejkl", LocalDateTime.now());
        Post post8 = new Post("5e80696a1e740b7140bf4419", "sishian2", "post8", "fewljfewiowjejkl", LocalDateTime.now());
        Post post9 = new Post("5e80696a1e740b7140bf441a", "sishian3", "post9", "fewljfewiowjejkl", LocalDateTime.now());
        postList.add(post1);
        postList.add(post2);
        postList.add(post3);
        postList.add(post4);
        postList.add(post5);
        postList.add(post6);
        postList.add(post7);
        postList.add(post8);
        postList.add(post9);
        logger.debug(postList.toString());
        postRepository.saveAll(postList);
    }

    @Test
    void deletePostByIdTest() {
        postRepository.deleteByUserId("5e80696a1e740b7140bf4418");
    }

    @Autowired
    GridFsOperations gridFsOperations;

    @Test
    void gridFsPictureTest() throws IOException {
        try (InputStream is = new BufferedInputStream(new ClassPathResource("static/avatar3.jpg").getInputStream())) {
            // store file
            gridFsOperations.store(is, "avatar3.jpg");

        }
        GridFSFile file = gridFsOperations.findOne(new Query(Criteria.where("filename").is("avatar3.jpg")));
//		GridFSFile file = gridFsOperations.findOne(Query.query(whereFilename().is("crud.png")));
        assert file != null;
        System.out.print(file.toString());

    }

    @Test
    void gridFsPictureTest2() throws IOException {
        try (InputStream is = new BufferedInputStream(new ClassPathResource("static/avatar-default.png").getInputStream())) {
            // store file
            gridFsOperations.store(is, "avatar-default.png");

        }
//        GridFSFile file = gridFsOperations.findOne(new Query(Criteria.where("filename").is("avatar-default.png")));
////		GridFSFile file = gridFsOperations.findOne(Query.query(whereFilename().is("crud.png")));
//        assert file != null;
//        System.out.print(file.toString());

    }


    @Test
    void gridFsVideoTest() throws IOException {
        try (InputStream is = new BufferedInputStream(new ClassPathResource("static/testVideo.mp4").getInputStream())) {
            // store file
            gridFsOperations.store(is, "testVideo.mp4");

        }
        GridFSFile file = gridFsOperations.findOne(new Query(Criteria.where("filename").is("testVideo.mp4")));
//		GridFSFile file = gridFsOperations.findOne(Query.query(whereFilename().is("crud.png")));
        assert file != null;
        System.out.print(file.toString());

    }

}
