package com.connortest.service;

import com.connortest.entity.RestaurantReview;
import com.connortest.repository.RestaurantReviewRepository;
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

    public boolean registerRestaurant(RestaurantReview restaurantReview){
        try {
            restaurantReviewRepository.save(restaurantReview);
        } catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public List<String> findByRating(int rating){
        return restaurantReviewRepository.findByRating(rating);
    }
}
