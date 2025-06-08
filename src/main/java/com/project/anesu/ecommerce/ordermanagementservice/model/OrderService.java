package com.project.anesu.ecommerce.ordermanagementservice.model;

import com.project.anesu.ecommerce.ordermanagementservice.entity.address.Address;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderItem;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderStatus;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.OrderNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing order-related operations within the Order Management Service.
 *
 * <p>This interface defines the contract for creating, retrieving, updating, and canceling orders.
 */
public interface OrderService {

  /**
   * Creates a new order.
   *
   * @param order the {@link Order} entity to be created
   * @return the created {@link Order} object
   */
  Order createOrder(Order order, List<OrderItem> orderItems);

  Order processPendingOrder(Long orderId, OrderStatus status) throws OrderNotFoundException;

  Order markOrderAsDelivered(Long orderId, OrderStatus status) throws OrderNotFoundException;

  /**
   * Retrieves an order by its ID and associated customer ID.
   *
   * @param orderId the ID of the order to retrieve
   * @return an {@link Optional} containing the order if found, or empty if not
   */
  Order getOrderById(Long orderId);

  /**
   * Retrieves all orders.
   *
   * @return a list of all {@link Order} objects
   */
  List<Order> getAllOrders();

  /**
   * Updates an existing order by its ID.
   *
   * @param orderId the ID of the order to update
   * @param updatedOrder the {@link Order} entity containing updated information
   * @return the updated {@link Order} object
   */
  Order updateDeliveryAddress(Long orderId, Long addressId, Address updatedOrder)
      throws OrderNotFoundException;

  /**
   * Cancels an existing order by its ID.
   *
   * @param orderId the ID of the order to cancel
   */
  void cancelOrder(Long orderId, String cancellationReason);
}
