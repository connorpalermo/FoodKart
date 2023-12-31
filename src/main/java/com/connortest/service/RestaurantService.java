package com.connortest.service;

import com.connortest.entity.Restaurant;
import com.connortest.repository.RestaurantRepository;
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

    public boolean registerRestaurant(Restaurant restaurant){
        try {
            restaurantRepository.save(restaurant);
        } catch(Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean updateQuantity(String name, int quantity){
        Restaurant restaurant = restaurantRepository.findByName(name);
        if(restaurant == null){
            log.error("Restaurant with ID: " + name + " does not exist!");
            return false;
        }
        restaurant.setItemQuantity(restaurant.getItemQuantity() + quantity);
        restaurantRepository.save(restaurant);
        return true;
    }

    public boolean placeOrder(String name, int quantity){
        Restaurant restaurant = restaurantRepository.findByName(name);
        if(restaurant == null){
            log.error("Restaurant with ID: " + name + " does not exist!");
            return false;
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
