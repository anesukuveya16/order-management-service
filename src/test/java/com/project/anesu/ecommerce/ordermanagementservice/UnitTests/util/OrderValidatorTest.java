package com.project.anesu.ecommerce.ordermanagementservice.UnitTests.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderItem;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.InvalidOrderException;
import com.project.anesu.ecommerce.ordermanagementservice.service.util.OrderValidator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderValidatorTest {

  private OrderValidator cut;

  @BeforeEach
  void setUp() {
    cut = new OrderValidator();
  }

  @Test
  void shouldThrowExceptionWhen_CustomerIdIsNull() {
    // Given
    Order order = new Order();
    order.setCustomerId(null);
    order.setOrderItem(List.of());
    order.setTotalPrice(23.60);

    // When
    InvalidOrderException exception =
        assertThrows(
            InvalidOrderException.class, () -> cut.validateNewOrder(order, order.getOrderItem()));

    // Then

    assertEquals("Customer ID cannot be null.", exception.getMessage());
  }

  @Test
  void shouldThrowException_WhenOrderItemIsNull() {
    // Given

    Long customerId = 1L;

    Order order = new Order();
    order.setCustomerId(customerId);
    order.setOrderItem(null);

    // When

    InvalidOrderException exception =
        assertThrows(
            InvalidOrderException.class, () -> cut.validateNewOrder(order, order.getOrderItem()));

    // Then

    assertEquals("Order item must contain at least one item!", exception.getMessage());
  }

  @Test
  void shouldThrowException_WhenProductIdIsNull_ForOrderItem() {
    // Given

    Order order = new Order();
    order.setCustomerId(2L);

    OrderItem orderItem = new OrderItem();
    orderItem.setProductId(null);
    orderItem.setQuantity(10);

    // When

    InvalidOrderException exception =
        assertThrows(
            InvalidOrderException.class,
            () -> cut.validateNewOrder(order, (List<OrderItem>) orderItem.getOrder()));

    // Then
    assertEquals("Product ID cannot be null.", exception.getMessage());
  }

  @Test
  void shouldThrowException_WhenOrderItemsIsNull_ForOrder() {
    // Given

    Order order = new Order();
    order.setCustomerId(2L);
    order.setOrderItem(List.of());

    OrderItem orderItem = new OrderItem();
    orderItem.setProductId(2L);
    orderItem.setQuantity(0);

    // When

    InvalidOrderException exception =
        assertThrows(
            InvalidOrderException.class,
            () -> cut.validateNewOrder(order, orderItem.getOrder().getOrderItem()));

    // Then
    assertEquals("Quantity must be greater than 0.", exception.getMessage());
  }
}
