package com.project.anesu.ecommerce.ordermanagementservice.entity.order;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_order")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long customerId;
  private double totalPrice;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private LocalDateTime orderDate;
  private String cancellationReason;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<OrderItem> orderItem;

}
