package com.foodkart.controller;

import com.foodkart.entity.Restaurant;
import com.foodkart.service.RestaurantService;
import com.foodkart.service.UsersService;
import com.foodkart.exception.RestaurantDoesNotExistException;
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
        Restaurant r1 = restaurantService.registerRestaurant(restaurant);
        if(r1 == null){
            return ResponseEntity.badRequest().body("Failed to register restaurant!");
        }
        return ResponseEntity.ok().body("Restaurant registered successfully with ID: " + r1.getId());
    }

    @PutMapping("/updateQuantity/{restaurantNumber}/{itemQuantity}")
    public ResponseEntity<String> updateQuantity(@PathVariable int restaurantNumber, @PathVariable int itemQuantity){
        boolean success = false;
        try {
            success = restaurantService.updateQuantity(restaurantNumber, itemQuantity);
        } catch (RestaurantDoesNotExistException e){
            log.error(e.getMessage());
        }
        if(!success){
            return ResponseEntity.badRequest().body("Failed to update quantity!");
        }
        return ResponseEntity.ok().body("Quantity updated successfully");
    }

    @PostMapping("/placeOrder/{restaurantNumber}/{itemQuantity}")
    public ResponseEntity<String> placeOrder(@PathVariable int restaurantNumber, @PathVariable int itemQuantity){
        boolean success = false;
        try {
            success = restaurantService.placeOrder(restaurantNumber, itemQuantity);
        } catch (RestaurantDoesNotExistException e){
            log.error(e.getMessage());
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
