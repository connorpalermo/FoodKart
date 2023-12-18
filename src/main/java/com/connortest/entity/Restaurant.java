package com.connortest.entity;

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

    @Column(name = "name")
    private String name;

    @Column(name = "pincodes")
    private String pincodes;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "food_price")
    private String foodPrice;

    @Column(name = "item_quantity")
    private int itemQuantity;

    public Restaurant(){
        this.id = 0;
        this.name = "NullRestaurant";
        this.pincodes = "000";
        this.foodName = "NO FOOD";
        this.foodPrice = "NO PRICE";
        this.itemQuantity = 0;
    }
}
