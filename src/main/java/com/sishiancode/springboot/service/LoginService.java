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

    public String loginUser(String phoneNumber, String password) {
        UserIdDTO userIdDTO = userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (userIdDTO == null) {
            return null;
        } else {
            return userIdDTO.getId();
        }
    }

    public String signUpUser(String phoneNumber, String username, String password) {
        //在已经存在的用户里找这个手机号
        UserIdDTO userIdDTO = userRepository.findByPhoneNumber(phoneNumber);
        if (userIdDTO != null) {
            //在已经存在的用户里找到了这个手机号
            logger.debug("SignUpUserButFindUserExist:" + userIdDTO.toString());
            return "null";
        } else {
            User user = new User(username, password, phoneNumber);
            User userWithId = userRepository.save(user);
            //创建关联的profile，创建默认头像
            try (InputStream is = new BufferedInputStream(new ClassPathResource("static/default-avatar.png").getInputStream())) {
                ObjectId id = gridFsOperations.store(is, "default-avatar.png");
                Profile profile = new Profile((userWithId.getId()), userWithId.getUsername(), userWithId.getPhoneNumber(), id.toString());
                profileRepository.save(profile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return userWithId.getId();
        }
    }

    public String loginAdmin(String phoneNumber, String password) {
        AdministratorDTO administratorDTO = administratorRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (administratorDTO == null) {
            return "null";
        } else {
            return administratorDTO.getId();
        }
    }
}
