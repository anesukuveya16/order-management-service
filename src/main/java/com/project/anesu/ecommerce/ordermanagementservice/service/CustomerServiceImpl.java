package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.model.CustomerService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.CustomerRepository;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.CustomerNotFoundException;
import java.util.List;
import java.util.Optional;

import com.project.anesu.ecommerce.ordermanagementservice.service.util.CustomerValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerValidator customerValidator;

  private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer not found.";

  @Override
  public Customer createCustomer(Customer customer) {

    customerValidator.validate(customer);
    return customerRepository.save(customer);
  }

  @Override
  public Customer updateCustomer(Long customerId, Customer updatedCustomer) throws CustomerNotFoundException {

    Customer existingCustomer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException(CUSTOMER_NOT_FOUND_MESSAGE));

    existingCustomer.setEmail(updatedCustomer.getEmail());
    existingCustomer.setFirstName(updatedCustomer.getFirstName());
    existingCustomer.setLastName(updatedCustomer.getLastName());
    existingCustomer.setBirthDate(updatedCustomer.getBirthDate());
    existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

    return customerRepository.save(existingCustomer);
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
