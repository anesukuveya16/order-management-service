package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.model.OrderService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Optional<Order> getOrderByCustomerId(Long orderId, Long customerId) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {

    }
}
