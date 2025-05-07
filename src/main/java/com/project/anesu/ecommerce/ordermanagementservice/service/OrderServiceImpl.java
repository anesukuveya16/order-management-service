package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.model.OrderService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.OrderRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Order not found";

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return null;
    }

    @Override
    public Optional<Order> getOrderByCustomerId(Long orderId, Long customerId) {
        return orderRepository.findById(customerId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order updateOrder(Long orderId, Order updatedOrder) {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE + orderId);
        }

        orderRepository.deleteById(orderId);
    }
}
