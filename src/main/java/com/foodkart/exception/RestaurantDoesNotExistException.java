package com.foodkart.exception;

public class RestaurantDoesNotExistException extends Exception {
    public RestaurantDoesNotExistException(String message){
        super(message);
    }
}
