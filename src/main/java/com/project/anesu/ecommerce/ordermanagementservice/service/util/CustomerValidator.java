package com.project.anesu.ecommerce.ordermanagementservice.service.util;

import com.project.anesu.ecommerce.ordermanagementservice.entity.customer.Customer;
import com.project.anesu.ecommerce.ordermanagementservice.service.exception.InvalidCustomerException;
import org.springframework.stereotype.Component;

@Component
public class CustomerValidator {

    public void validate(Customer customer) {

        validateCustomerInfo(customer);
        validateCustomerEmail(customer, customer.getEmail());
    }

    private void validateCustomerInfo(Customer customer) throws InvalidCustomerException {

        if (customer.getId() == null) {
            throw new InvalidCustomerException("Customer id is required!");
        }

        if (customer.getFirstName() == null || customer.getLastName() == null) {
            throw new InvalidCustomerException("First or last name is null!");
        }
    }

    private void validateCustomerEmail(Customer customer, String email) throws InvalidCustomerException {
        if (email.equals(customer.getEmail())) {
            throw new InvalidCustomerException("Email is already in use!");
        }

        if (customer.getEmail() == null){
            throw new InvalidCustomerException("Valid email is required!");
        }
    }
}
