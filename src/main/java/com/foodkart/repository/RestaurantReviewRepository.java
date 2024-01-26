package com.foodkart.repository;

import com.foodkart.entity.RestaurantReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantReviewRepository extends JpaRepository<RestaurantReview, Integer> {

    @Query(value = """
            select name from (select AVG(rating) as average, name\s
            from restaurant_review
            group by (name)) as avg_table where avg_table.average >= ?1""", nativeQuery = true)
    List<String> findByRating(int rating);

}
