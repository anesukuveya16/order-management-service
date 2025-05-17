package com.project.anesu.ecommerce.ordermanagementservice.service.util;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderItem;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.InvalidOrderException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidation {

  public void validateOrder(Order order, OrderItem orderItem) {
    validateNewlyCreatedOrder(order);
    validateOrderItem(orderItem);
  }

  private void validateNewlyCreatedOrder(Order order)  {
    if (order == null) {
      throw new InvalidOrderException("Order cannot be null.");
    }

    if (order.getCustomerId() == null) {
      throw new InvalidOrderException("Customer ID cannot be null.");
    }

    if (order.getOrderItem() == null) {
      throw new InvalidOrderException("Order item must contain at least one item.");
    }
  }



  private void validateOrderItem(OrderItem orderItem)  {
    if (orderItem == null) {
      throw new InvalidOrderException("Quantity must be greater than 0.");
    }

    if (orderItem.getProductId() == null || orderItem.getProductId() <= 0) {
      throw new InvalidOrderException("Product ID must be greater than 0.");
    }
  }
}
