package com.project.anesu.ecommerce.ordermanagementservice.model;


import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService  {

    Order createOrder(Order order);

    Optional<Order> getOrderByCustomerId(Long orderId, Long customerId);

    List<Order> getAllOrders();

    Order updateOrder(Long orderId, Order updatedOrder);

    void cancelOrder(Long orderId);
}
