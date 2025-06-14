package com.project.anesu.ecommerce.ordermanagementservice.controller.order;

import static com.project.anesu.ecommerce.ordermanagementservice.controller.order.OrderServiceRestEndpoints.GET_ALL_ORDERS;
import static com.project.anesu.ecommerce.ordermanagementservice.controller.order.OrderServiceRestEndpoints.LANDING_PAGE;

import com.project.anesu.ecommerce.ordermanagementservice.entity.address.Address;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderStatus;
import com.project.anesu.ecommerce.ordermanagementservice.model.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(LANDING_PAGE)
public class OrderController {

  private final OrderService orderService;

  @PostMapping(OrderServiceRestEndpoints.CREATE_ORDER)
  public Order createOrder(@RequestBody Order order) {
    return orderService.createOrder(order, order.getOrderItem());
  }

  @GetMapping(GET_ALL_ORDERS)
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping(OrderServiceRestEndpoints.GET_ORDER_BY_ID)
  public Order getOrderById(@PathVariable Long orderId) {
    return orderService.getOrderById(orderId);
  }

  @PutMapping(OrderServiceRestEndpoints.PROCESS_ORDER)
  public Order processPendingOrder(@PathVariable Long orderId) {
    return orderService.processPendingOrder(orderId, OrderStatus.PROCESSING);
  }

  @PutMapping(OrderServiceRestEndpoints.MARK_AS_DELIVERED)
  public Order markOrderAsDelivered(@PathVariable Long orderId) {
    return orderService.markOrderAsDelivered(orderId, OrderStatus.OUT_FOR_DELIVERY);
  }

  @PutMapping(OrderServiceRestEndpoints.CANCEL_ORDER)
  public void cancelOrder(@PathVariable Long orderId, @RequestParam String reason) {
    orderService.cancelOrder(orderId, reason);
  }

  @PutMapping(OrderServiceRestEndpoints.UPDATE_DELIVERY_ADDRESS)
  public Order updateDeliveryAddress(
      @PathVariable Long orderId,
      @PathVariable Long addressId,
      @RequestBody Address updatedAddress) {

    return orderService.updateDeliveryAddress(orderId, addressId, updatedAddress);
  }
}
