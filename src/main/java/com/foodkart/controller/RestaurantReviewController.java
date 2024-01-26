package com.foodkart.controller;

import com.foodkart.entity.RestaurantReview;
import com.foodkart.service.RestaurantReviewService;
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
        RestaurantReview review = restaurantReviewService.registerRestaurantReview(restaurantReview);
        if(review == null){
            return ResponseEntity.badRequest().body("Unable to create restaurant review!");
        }
        return ResponseEntity.ok().body("Restaurant review created with ID: " + review.getId());
    }

    @GetMapping("/findByRating")
    public ResponseEntity<List<String>> listByRating(@RequestParam int rating){
        List<String> restaurants = restaurantReviewService.findByRating(rating);
        return ResponseEntity.ok().body(restaurants);
    }
}
