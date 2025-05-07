package com.project.anesu.ecommerce.ordermanagementservice.service;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.model.CustomerService;
import com.project.anesu.ecommerce.ordermanagementservice.model.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

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
    public Optional<Customer> getCustomer(Long customerId) {
        return Optional.empty();
    }

    @Override
    public List<Customer> getAllCustomers() {
        return List.of();
    }

    @Override
    public void deleteCustomer(Long customerId) {

    }
}
