package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.model.CustomerService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.CustomerRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found";

  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
    return null;
  }

  @Override
  public Optional<Customer> getCustomerById(Long customerId) {
    return customerRepository.findById(customerId);
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public void deleteCustomer(Long customerId) {
    if (!customerRepository.existsById(customerId)) {
      throw new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE + customerId);
    }
    customerRepository.deleteById(customerId);
  }
}
