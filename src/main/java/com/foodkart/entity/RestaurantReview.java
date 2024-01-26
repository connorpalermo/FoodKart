package com.foodkart.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_review")
@Getter
@Setter
public class RestaurantReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable=false)
    private int id;

    @Column(name = "review_number",nullable=false)
    private int reviewNumber;

    @Column(name = "name",nullable=false)
    private String name;

    @Column(name = "rating",nullable=false)
    private int rating;

    @Column(name = "comment")
    private String comment;
}
