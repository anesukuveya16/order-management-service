package com.project.anesu.ecommerce.ordermanagementservice.service.exception;

public class CustomerNotFoundException extends RuntimeException {
  public CustomerNotFoundException(String message) {
    super(message);
  }
}
