package com.foodkart.repository;

import com.foodkart.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findByRestaurantNumber(int restaurantNumber);

    @Query(value = "SELECT * FROM restaurant WHERE food_price <= ?1", nativeQuery = true)
    List<Restaurant> findByFoodPrice(int price);

}
