package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderStatus;
import com.project.anesu.ecommerce.ordermanagementservice.model.OrderService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.OrderRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.CustomerNotFoundException;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.OrderNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final RestTemplate restTemplate;
  private final  String stockServiceBaseUrl = "http://localhost:8081";

  private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Order not found";

  @Override
  public Order createOrder(Order order) throws OrderNotFoundException{



    //

    order.setOrderDate(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.PROCESSING);

    return null;
  }


  @Override
  public Order processPendingOrder( Long orderId, OrderStatus status) throws OrderNotFoundException {

    Order orderRequest = getOrderByIdAndStatus(orderId, OrderStatus.PROCESSING);
    orderRequest.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
    return orderRepository.save(orderRequest);
  }

  @Override
  public Order markOrderAsDelivered(Long orderId, OrderStatus status) throws OrderNotFoundException {

    Order orderRequest = getOrderByIdAndStatus(orderId, OrderStatus.OUT_FOR_DELIVERY);
    orderRequest.setOrderStatus(OrderStatus.DELIVERED);
    return orderRepository.save(orderRequest);
  }

  @Override
  public Optional<Order> getOrderByCustomerId(Long orderId, Long customerId) {

    return orderRepository.findById(customerId);
  }

  public Order getOrderByIdAndStatus(Long orderId, OrderStatus status)  throws OrderNotFoundException {

    return orderRepository.findByIdAndStatus(orderId, status)
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE + orderId));
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
  public void cancelOrder(Long orderId, String cancellationReason) throws OrderNotFoundException {

    Order orderRequest = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE));

    orderRequest.setOrderStatus(OrderStatus.CANCELLED);
    orderRequest.setCancellationReason(cancellationReason);
    orderRepository.save(orderRequest);

  }
}
