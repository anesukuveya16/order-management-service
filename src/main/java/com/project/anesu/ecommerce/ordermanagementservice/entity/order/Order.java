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
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerId;
  private double totalPrice;
  private OrderStatus orderStatus;
  private LocalDateTime orderDate;
  private String cancellationReason;
  private @OneToMany List<OrderItem> orderItem;
}
