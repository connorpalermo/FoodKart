package com.foodkart.service;

import com.foodkart.entity.Restaurant;
import com.foodkart.repository.RestaurantRepository;
import com.foodkart.exception.RestaurantDoesNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant registerRestaurant(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public boolean updateQuantity(int restaurantNumber, int quantity) throws RestaurantDoesNotExistException {
        Restaurant restaurant = restaurantRepository.findByRestaurantNumber(restaurantNumber);
        if(restaurant == null){
            throw new RestaurantDoesNotExistException("Restaurant with name: " + restaurantNumber + " does not exist.");
        }
        restaurant.setItemQuantity(restaurant.getItemQuantity() + quantity);
        restaurantRepository.save(restaurant);
        return true;
    }

    public boolean placeOrder(int restaurantNumber, int quantity) throws RestaurantDoesNotExistException {
        Restaurant restaurant = restaurantRepository.findByRestaurantNumber(restaurantNumber);
        if(restaurant == null){
            throw new RestaurantDoesNotExistException("Restaurant with name: " + restaurantNumber + " does not exist.");
        }
        else if(quantity > restaurant.getItemQuantity()){
            log.error("Item quantity is too high. Cannot place order");
            return false;
        }
        return true;
    }

    public List<String> findByPrice(int price, String pincode){
        List<Restaurant> restaurants = restaurantRepository.findByFoodPrice(price);
        List<String> names = new LinkedList<>();
        for(Restaurant r : restaurants){
            String[] pincodes = r.getPincodes().split("/");
            for(String code : pincodes){
                if(code.equals(pincode)){
                    names.add(r.getName());
                    break;
                }
            }
        }
        return names;
    }
}
