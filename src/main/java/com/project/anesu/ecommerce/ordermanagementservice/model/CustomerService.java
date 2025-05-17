package com.project.anesu.ecommerce.ordermanagementservice.model;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing customer-related operations within the Order Management Service.
 *
 * <p>This interface defines the contract for creating, updating, retrieving, and deleting customer
 * records.
 */
public interface CustomerService {

  /**
   * Creates a new customer record.
   *
   * @param customer the customer entity to be created
   * @return the created {@link Customer} object
   */
  Customer createCustomer(Customer customer);

  /**
   * Updates an existing customer record by ID.
   *
   * @param customerId the ID of the customer to update
   * @param updatedCustomer the customer entity containing updated information
   * @return the updated {@link Customer} object
   */
  Customer updateCustomer(Long customerId, Customer updatedCustomer);

  /**
   * Retrieves a customer by their ID.
   *
   * @param customerId the ID of the customer to retrieve
   * @return an {@link Optional} containing the customer if found, or empty if not
   */
  Optional<Customer> getCustomerById(Long customerId);

  /**
   * Retrieves a list of all customers.
   *
   * @return a list of all {@link Customer} objects
   */
  List<Customer> getAllCustomers();

  /**
   * Deletes a customer by their ID.
   *
   * @param customerId the ID of the customer to delete
   */
  void deleteCustomer(Long customerId);
}
