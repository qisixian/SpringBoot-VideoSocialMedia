package com.sishiancode.springboot.service;

import com.sishiancode.springboot.dto.AdministratorDTO;
import com.sishiancode.springboot.dto.UserIdDTO;
import com.sishiancode.springboot.entities.Profile;
import com.sishiancode.springboot.entities.User;
import org.bson.types.ObjectId;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class LoginService extends BaseService {

    public String LoginUser(String phoneNumber, String password) {
        UserIdDTO userIdDTO = userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (userIdDTO == null) {
            return "null";
        } else {
            return userIdDTO.getId();
        }
    }

    public String SignUpUser(String phoneNumber, String username, String password) throws IOException {
        //在已经存在的用户里找这个手机号
        UserIdDTO userIdDTO = userRepository.findByPhoneNumber(phoneNumber);
        if (userIdDTO != null) {
            //在已经存在的用户里找到了这个手机号
            logger.debug("SignUpUserButFindUserExist:" + userIdDTO.toString());
            logger.debug("SignUpUserButFindUserExist:" + userIdDTO.getId());
            return "null";
        } else {

            User user = new User(username, password, phoneNumber);
            User userWithId = userRepository.save(user);

            //创建关联的profile，创建默认头像
            ObjectId id;
            try (InputStream is = new BufferedInputStream(new ClassPathResource("static/default-avatar.png").getInputStream())) {
                // store file
                id = gridFsOperations.store(is, "default-avatar.png");
            }
            Profile profile = new Profile((userWithId.getId()), userWithId.getUsername(), userWithId.getPhoneNumber(), id.toString());
            profileRepository.save(profile);
            return userWithId.getId();
        }

    }

    public String LoginAdmin(String phoneNumber, String password) {
        AdministratorDTO administratorDTO = administratorRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (administratorDTO == null) {
            return "null";
        } else {
            return administratorDTO.getId();
        }
    }
}
