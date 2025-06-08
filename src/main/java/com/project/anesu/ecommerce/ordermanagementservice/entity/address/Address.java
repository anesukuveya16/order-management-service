package com.project.anesu.ecommerce.ordermanagementservice.entity.address;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String streetName;
  private String streetNumber;
  private String city;
  private String state;
  private String zipCode;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
}
