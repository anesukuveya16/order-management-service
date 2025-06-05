package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderItem;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderStatus;
import com.project.anesu.ecommerce.ordermanagementservice.model.OrderService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.OrderRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.OrderNotFoundException;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.ValidationFailedException;
import com.project.anesu.ecommerce.ordermanagementservice.service.util.OrderValidator;
import java.time.LocalDateTime;
import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final RestTemplate restTemplate;
  private final OrderValidator orderValidator;
  private final OrderRepository orderRepository;
  private static final String VALIDATE_ORDER = "http://localhost:8081";
  private static final String ORDER_NOT_FOUND_EXCEPTION_MESSAGE = "Order not found";

  @Override
  public Order createOrder(Order order, List<OrderItem> orderItems) throws OrderNotFoundException {

    validateNewOrder(order, orderItems);

    List<Map<String, Object>> batchInventoryValidationRequest = new ArrayList<>();
    for (OrderItem orderItem : orderItems) {
      Map<String, Object> orderItemData = new HashMap<>();
      orderItemData.put("productId", orderItem.getProductId());
      orderItemData.put("quantity", orderItem.getQuantity());
      batchInventoryValidationRequest.add(orderItemData);
    }

    String validationUrl = VALIDATE_ORDER + "/api/stock/validate-and-deduct-product";

    try {
      ResponseEntity<String> validationResponse =
          restTemplate.postForEntity(validationUrl, batchInventoryValidationRequest, String.class);

      // the service itself could not process the order entirely
      if (validationResponse.getStatusCode() != HttpStatus.OK) {
        throw new IllegalStateException("An error occurred while validating order.");
      }
      // while making the request of validation
    } catch (HttpClientErrorException e) {
      throw new ValidationFailedException(
          "Order validation has failed" + e.getResponseBodyAsString());
    } catch (RestClientException e) {
      throw new RestClientException("Failed to connect to Product Service" + e.getMessage());
    }

    order.setCustomerId(order.getCustomerId());
    order.setOrderDate(LocalDateTime.now());
    order.setOrderStatus(OrderStatus.PROCESSING);

   for (OrderItem orderItem : orderItems) {
     orderItem.setOrder(order);
   }
   order.setOrderItem(orderItems);

    return orderRepository.save(order);
  }

  @Override
  public Order processPendingOrder(Long orderId, OrderStatus status) throws OrderNotFoundException {

    Order orderRequest = getOrderByIdAndStatus(orderId, OrderStatus.PROCESSING);
    orderRequest.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
    return orderRepository.save(orderRequest);
  }

  @Override
  public Order markOrderAsDelivered(Long orderId, OrderStatus status)
      throws OrderNotFoundException {

    Order orderRequest = getOrderByIdAndStatus(orderId, OrderStatus.OUT_FOR_DELIVERY);
    orderRequest.setOrderStatus(OrderStatus.DELIVERED);
    return orderRepository.save(orderRequest);
  }

  @Override
  public Optional<Order> getOrderByCustomerId(Long orderId, Long customerId) {

    return orderRepository.findById(customerId);
  }

  public Order getOrderByIdAndStatus(Long orderId, OrderStatus status)
      throws OrderNotFoundException {

    return orderRepository
        .findByIdAndOrderStatus(orderId, status)
        .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE + orderId));
  }

  @Override
  public List<Order> getAllOrders() {

    return orderRepository.findAll();
  }

  @Override
  public Order updateOrder(Long orderId, Order updatedOrder) throws OrderNotFoundException {

    Order existingOrder = orderRepository.findById(orderId).
            orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE + orderId));

    existingOrder.setOrderDate(updatedOrder.getOrderDate());
    existingOrder.setOrderItem(updatedOrder.getOrderItem());
    existingOrder.setTotalPrice(updatedOrder.getTotalPrice());
    existingOrder.setOrderStatus(updatedOrder.getOrderStatus());

    return orderRepository.save(existingOrder);
  }

  @Override
  public void cancelOrder(Long orderId, String cancellationReason) throws OrderNotFoundException {

    Order orderRequest =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(ORDER_NOT_FOUND_EXCEPTION_MESSAGE));

    orderRequest.setOrderStatus(OrderStatus.CANCELLED);
    orderRequest.setCancellationReason(cancellationReason);
    orderRepository.save(orderRequest);
  }

  private void validateNewOrder(Order order, List<OrderItem> orderItems) {
    orderValidator.validateNewOrder(order, orderItems);
  }
}
