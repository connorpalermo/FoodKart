package com.connortest.repository;

import com.connortest.entity.RestaurantReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Integer> {

    @Query(value = "SELECT name FROM restaurant_review WHERE rating = ?1", nativeQuery = true)
    List<String> findByRating(int rating);

}
