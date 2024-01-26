package com.foodkart.service;

import com.foodkart.entity.RestaurantReview;
import com.foodkart.repository.RestaurantReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantReviewService {

    private final RestaurantReviewRepository restaurantReviewRepository;

    @Autowired
    public RestaurantReviewService(RestaurantReviewRepository restaurantReviewRepository){
        this.restaurantReviewRepository = restaurantReviewRepository;
    }

    public RestaurantReview registerRestaurantReview(RestaurantReview restaurantReview){
        return restaurantReviewRepository.save(restaurantReview);
    }

    public List<String> findByRating(int rating){
        return restaurantReviewRepository.findByRating(rating);
    }
}
