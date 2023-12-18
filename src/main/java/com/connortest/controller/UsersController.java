package com.connortest.controller;

import com.connortest.entity.Users;
import com.connortest.service.UsersService;
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

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody ObjectNode objectNode){
        boolean success = false;
        try {
            success = usersService.loginUser(objectNode.get("phone").textValue());
        } catch (NullPointerException e){
                log.error("Body params formatted incorrectly.");
            }
        if(!success){
            return ResponseEntity.badRequest().body("User was not logged in!");
        }
        return ResponseEntity.ok().body("Login successful");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user){
        boolean success = usersService.registerUser(user);
        if(!success){
            return ResponseEntity.badRequest().body("User could not be registered!");
        }
        return ResponseEntity.ok().body("User registered successfully");
    }
}
