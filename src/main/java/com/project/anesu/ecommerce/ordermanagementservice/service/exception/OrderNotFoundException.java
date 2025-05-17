package com.project.anesu.ecommerce.ordermanagementservice.service.exception;

public class OrderNotFoundException extends RuntimeException {
  public OrderNotFoundException(String message) {
    super(message);
  }
}
