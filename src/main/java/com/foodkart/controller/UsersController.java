package com.foodkart.controller;

import com.foodkart.entity.Users;
import com.foodkart.exception.UserDoesNotExistException;
import com.foodkart.service.UsersService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UsersController {

    private UsersService usersService;

    @Autowired
    public void setUsersService(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/login/{username}")
    public ResponseEntity<String> loginUser(@PathVariable String username){
        try {
            usersService.loginUser(username);
        } catch (NullPointerException e){
            log.error("Body params formatted incorrectly.");
        } catch (UserDoesNotExistException e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body("User " + username + " does not exist!");
        }
        return ResponseEntity.ok().body("Login successful for user: " + username);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user){
        Users u = usersService.registerUser(user);
        if(u == null){
            return ResponseEntity.badRequest().body("User could not be registered!");
        }
        return ResponseEntity.ok().body("User registered successfully with ID: " + u.getId());
    }
}
