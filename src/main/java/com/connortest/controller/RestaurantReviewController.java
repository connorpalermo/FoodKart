package com.connortest.controller;

import com.connortest.entity.RestaurantReview;
import com.connortest.service.RestaurantReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantReview")
@Slf4j
public class RestaurantReviewController {

    private RestaurantReviewService restaurantReviewService;

    @Autowired
    public void setRestaurantReviewService(RestaurantReviewService restaurantReviewService){
        this.restaurantReviewService = restaurantReviewService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerRestaurantReview(@RequestBody RestaurantReview restaurantReview){
        boolean success = restaurantReviewService.registerRestaurant(restaurantReview);
        if(!success){
            return ResponseEntity.badRequest().body("Unable to create restaurant review!");
        }
        return ResponseEntity.ok().body("Restaurant review created!");
    }

    @GetMapping("/findByRating")
    public ResponseEntity<List<String>> listByRating(@RequestParam int rating){
        List<String> restaurants = restaurantReviewService.findByRating(rating);
        return ResponseEntity.ok().body(restaurants);
    }
}
