package com.foodkart.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable=false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "restaurant_number",nullable=false)
    private int restaurantNumber;

    @Column(name = "pincodes", nullable = false)
    private String pincodes;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "food_price", nullable = false)
    private int foodPrice;

    @Column(name = "item_quantity", nullable = false)
    private int itemQuantity;

    public Restaurant(){
        this.id = 0;
        this.name = "NullRestaurant";
        this.pincodes = "000";
        this.foodName = "NO FOOD";
        this.foodPrice = 0;
        this.itemQuantity = 0;
    }
}
