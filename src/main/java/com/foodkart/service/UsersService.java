package com.foodkart.service;

import com.foodkart.entity.Users;
import com.foodkart.exception.UserDoesNotExistException;
import com.foodkart.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UsersService {

    private final UsersRepository usersRepository;

    private Users activeUser;

    @Autowired
    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
        activeUser = null;
    }

    public boolean loginUser(String username) throws UserDoesNotExistException {
        Users currentUser = usersRepository.findByUsername(username);
        if(currentUser == null){
            throw new UserDoesNotExistException("Username " + username + " does not exist.");
        }
        // not best practice, just for demonstration. In reality would authenticate using JWT/OAuth and provide a token to users
        // for API calls
        activeUser = currentUser;
        return true;
    }

    public Users registerUser(Users user){
        return usersRepository.save(user);
    }

    public Users getActiveUser(){
        return activeUser;
    }

}
