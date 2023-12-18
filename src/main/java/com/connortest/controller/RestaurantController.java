package com.connortest.controller;

import com.connortest.entity.Restaurant;
import com.connortest.service.RestaurantService;
import com.connortest.service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@Slf4j
public class RestaurantController {

    private RestaurantService restaurantService;
    private UsersService usersService;

    @Autowired
    public void setRestaurantService(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @Autowired
    public void setUsersService(UsersService usersService){
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRestaurant(@RequestBody Restaurant restaurant){
        boolean success = restaurantService.registerRestaurant(restaurant);
        if(!success){
            return ResponseEntity.badRequest().body("Failed to register restaurant!");
        }
        return ResponseEntity.ok().body("Restaurant registered successfully");
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestBody ObjectNode objectNode){
        boolean success = false;
        try {
            success = restaurantService.updateQuantity(objectNode.get("name").textValue(), objectNode.get("itemQuantity").intValue());
        } catch (NullPointerException e){
            log.error("Body params formatted incorrectly.");
        }
        if(!success){
            return ResponseEntity.badRequest().body("Failed to update quantity!");
        }
        return ResponseEntity.ok().body("Quantity updated successfully");
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<String> placeOrder(@RequestBody ObjectNode objectNode){
        boolean success = false;
        try {
            success = restaurantService.placeOrder(objectNode.get("name").textValue(), objectNode.get("itemQuantity").intValue());
        } catch (NullPointerException e){
            log.error("Body params formatted incorrectly.");
        }
        if(!success){
            return ResponseEntity.badRequest().body("Order cannot be placed!");
        }
        return ResponseEntity.ok().body("Order placed successfully");
    }

    @GetMapping("/findByPrice")
    public ResponseEntity<List<String>> findByPrice(@RequestParam int price){
        String activePincode;
        try {
             activePincode = usersService.getActiveUser().getPincode();
        } catch(NullPointerException e){
            log.error("User is not signed in! Please login first.");
            return ResponseEntity.internalServerError().body(null);
        }
        List<String> restaurants = restaurantService.findByPrice(price, activePincode);
        return ResponseEntity.ok().body(restaurants);
    }
}
