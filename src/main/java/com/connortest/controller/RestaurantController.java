package com.connortest.controller;

import com.connortest.entity.Restaurant;
import com.connortest.service.RestaurantService;
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

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<String> registerRestaurant(@RequestBody Restaurant restaurant){
        boolean success = restaurantService.registerRestaurant(restaurant);
        if(!success){
            return ResponseEntity.badRequest().body("Failed to register restaurant!");
        }
        return ResponseEntity.accepted().body("Restaurant registered successfully");
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
        return ResponseEntity.accepted().body("Quantity updated successfully");
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
        return ResponseEntity.accepted().body("Order placed successfully");
    }

    @GetMapping("/findByPrice")
    public ResponseEntity<List<String>> findByPrice(@RequestParam int price){
        List<String> restaurants = restaurantService.findByPrice(price);
        if(restaurants.isEmpty()){
            return ResponseEntity.badRequest().body(restaurants);
        }
        return ResponseEntity.accepted().body(restaurants);
    }
}
