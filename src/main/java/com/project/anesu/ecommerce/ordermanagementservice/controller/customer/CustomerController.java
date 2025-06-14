package com.project.anesu.ecommerce.ordermanagementservice.controller.customer;

import static com.project.anesu.ecommerce.ordermanagementservice.controller.customer.CustomerServiceRestEndpoints.*;

import com.project.anesu.ecommerce.ordermanagementservice.entity.address.Address;
import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.model.CustomerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(LANDING_PAGE)
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping(CustomerServiceRestEndpoints.CREATE_CUSTOMER)
  public Customer createCustomer(@RequestBody Customer customer) {
    return customerService.createCustomer(customer);
  }

  @PutMapping(CustomerServiceRestEndpoints.UPDATE_CUSTOMER)
  public Customer updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
    return customerService.updateCustomer(customerId, customer);
  }

  @GetMapping(CustomerServiceRestEndpoints.GET_CUSTOMER_BY_ID)
  public Customer getCustomerById(@PathVariable Long customerId) {
    return customerService.getCustomerById(customerId);
  }

  @GetMapping(CustomerServiceRestEndpoints.GET_ALL_CUSTOMERS)
  public List<Customer> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  @DeleteMapping(CustomerServiceRestEndpoints.DELETE_CUSTOMER)
  public void deleteCustomer(@PathVariable Long customerId) {
    customerService.deleteCustomer(customerId);
  }

  @PostMapping(CustomerServiceRestEndpoints.CUSTOMER_ADDRESSES)
  public Customer linkAddressToCustomer(
      @PathVariable Long customerId, @RequestBody Address address) {
    return customerService.linkDeliveryAddressToCustomer(customerId, address);
  }

  @PutMapping(CustomerServiceRestEndpoints.UPDATE_CUSTOMER_ADDRESS)
  public Customer updateCustomerAddress(
      @PathVariable Long customerId,
      @PathVariable Long addressId,
      @RequestBody Address updatedAddress) {
    return customerService.updateDeliveryAddressToCustomer(customerId, addressId, updatedAddress);
  }

  @DeleteMapping(CustomerServiceRestEndpoints.DELETE_GIVEN_CUSTOMER_ADDRESS)
  public Customer deleteCustomerAddress(
      @PathVariable Long customerId, @PathVariable Long addressId, @RequestBody Address address) {
    return customerService.deleteDeliveryAddressFromCustomer(customerId, addressId, address);
  }
}
