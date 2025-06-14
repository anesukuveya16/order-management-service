package com.project.anesu.ecommerce.ordermanagementservice.UnitTests;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.OrderStatus;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.OrderRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.OrderServiceImpl;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.OrderNotFoundException;
import com.project.anesu.ecommerce.ordermanagementservice.service.util.OrderValidator;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @Mock private RestTemplate restTemplateMock;
  @Mock private OrderRepository orderRepositoryMock;
  @Mock private OrderValidator orderValidatorMock;

  private OrderServiceImpl cut;

  @BeforeEach
  void setUp() {
    cut = new OrderServiceImpl(restTemplateMock, orderRepositoryMock, orderValidatorMock);
  }

  @Test
  void shouldSuccessfullyRetrieveRequestedOrder_ByGivenOrderId() {

    // Given
    Long orderId = 1L;
    Order order = new Order();
    order.setId(orderId);

    when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

    // When
    cut.getOrderById(orderId);

    // Then
    verify(orderRepositoryMock, times(1)).findById(orderId);
  }

  @Test
  void shouldThrowExceptionWhenOrderNotIsFound_ByGivenOrderId() {

    // Given
    Long orderId = 1L;
    when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

    // When
    assertThrows(OrderNotFoundException.class, () -> cut.getOrderById(orderId));

    // Then
    verify(orderRepositoryMock, times(1)).findById(orderId);
    verifyNoMoreInteractions(orderRepositoryMock);
  }

  @Test
  void shouldRetrieveAllOrdersSuccessfully() {

    // Given
    when(orderRepositoryMock.findAll()).thenReturn(List.of());

    // When
    cut.getAllOrders();

    // Then
    verify(orderRepositoryMock, times(1)).findAll();
  }

  @Test
  void shouldRetrieveOrderByIdAndStatusSuccessfully() {

    // Given
    Long orderId = 15L;
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(OrderStatus.PROCESSING);

    when(orderRepositoryMock.findByIdAndOrderStatus(orderId, order.getOrderStatus()))
        .thenReturn(Optional.of(order));

    // When
    Order retrievedOrder = cut.getOrderByIdAndStatus(orderId, order.getOrderStatus());

    // Then
    assertThat(retrievedOrder.getOrderStatus()).isEqualTo(order.getOrderStatus());

    verify(orderRepositoryMock, times(1)).findByIdAndOrderStatus(orderId, order.getOrderStatus());
  }

  @Test
  void processPendingOrder_shouldProcessNewCreatedOrderSuccessfully() {

    // Given
    Long orderId = 1L;
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(OrderStatus.PROCESSING);

    when(orderRepositoryMock.findByIdAndOrderStatus(orderId, order.getOrderStatus()))
        .thenReturn(Optional.of(order));
    when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);

    // When
    Order processOrder = cut.processPendingOrder(orderId, OrderStatus.OUT_FOR_DELIVERY);

    // Then
    assertThat(processOrder.getOrderStatus()).isEqualTo(OrderStatus.OUT_FOR_DELIVERY);

    verify(orderRepositoryMock, times(1)).save(order);
  }

  @Test
  void markOrderAsDelivered_ShouldUpdateNewStatusOfOrderSuccessfully() {

    // Given
    Long orderId = 1L;
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);

    when(orderRepositoryMock.findByIdAndOrderStatus(orderId, order.getOrderStatus()))
        .thenReturn(Optional.of(order));
    when(orderRepositoryMock.save(any(Order.class))).thenReturn(order);

    // When
    Order deliverOrder = cut.markOrderAsDelivered(orderId, OrderStatus.DELIVERED);

    // Then
    assertThat(deliverOrder.getOrderStatus()).isEqualTo(OrderStatus.DELIVERED);

    verify(orderRepositoryMock, times(1)).save(order);
  }

  @Test
  void shouldCancelOrderSuccessfully() {

    // Given
    Long orderId = 1L;
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(OrderStatus.PROCESSING);
    order.setCancellationReason("Reason");

    when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(order));

    // When
    cut.cancelOrder(orderId, order.getCancellationReason());

    // Then
    assertThat(order.getCancellationReason()).isEqualTo("Reason");

    verify(orderRepositoryMock, times(1)).save(order);
  }
}
