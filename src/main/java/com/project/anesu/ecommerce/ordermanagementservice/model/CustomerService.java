package com.project.anesu.ecommerce.ordermanagementservice.model;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Long customerId, Customer updatedCustomer);

    Optional <Customer> getCustomer(Long customerId);

    List<Customer> getAllCustomers();

    void deleteCustomer(Long customerId);
}
