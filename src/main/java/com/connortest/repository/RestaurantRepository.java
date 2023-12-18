package com.connortest.repository;

import com.connortest.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    Restaurant findByName(String name);

    @Query(value = "SELECT name FROM restaurant WHERE food_price <= ?1", nativeQuery = true)
    List<String> findByFoodPrice(int price);

}
