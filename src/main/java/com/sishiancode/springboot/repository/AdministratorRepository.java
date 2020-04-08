package com.sishiancode.springboot.repository;

import com.sishiancode.springboot.dto.AdministratorDTO;
import com.sishiancode.springboot.entities.Administrator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends MongoRepository<Administrator, String> {
    AdministratorDTO findByPhoneNumberAndPassword(String phoneNumber, String password);
}
