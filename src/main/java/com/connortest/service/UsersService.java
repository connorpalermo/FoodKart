package com.connortest.service;

import com.connortest.entity.Users;
import com.connortest.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private static Users activeUser;

    public boolean loginUser(String phone){
        Users currentUser = usersRepository.findByPhone(phone);
        if(currentUser == null){
            log.error("User with ID: " + phone + " does not exist!");
            return false;
        }
        activeUser = currentUser;
        return true;
    }

    public boolean registerUser(Users user){
        try {
            usersRepository.save(user);
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
