package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.dto.UserIdDTO;
import com.sishiancode.springboot.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    UserIdDTO findByPhoneNumberAndPassword(String phoneNumber, String password);

    UserIdDTO findByPhoneNumber(String phoneNumber);

}
