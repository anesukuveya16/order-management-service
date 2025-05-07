package com.project.anesu.ecommerce.ordermanagementservice.controller;

import com.project.anesu.ecommerce.ordermanagementservice.entity.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerId;
    private OrderStatus orderStatus;
}
