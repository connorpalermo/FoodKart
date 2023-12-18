package com.connortest.service;

import com.connortest.entity.RestaurantReview;
import com.connortest.repository.RestaurantReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantReviewService {

    @Autowired
    private RestaurantReviewRepository restaurantReviewRepository;

    public String registerRestaurant(RestaurantReview restaurantReview){
        restaurantReviewRepository.save(restaurantReview);
        return "Restaurant registered successfully!";
    }

    public List<String> findByRating(int rating){
        return restaurantReviewRepository.findByRating(rating);
    }
}
