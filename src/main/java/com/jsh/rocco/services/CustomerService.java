package com.jsh.rocco.services;


import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;


    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public Customer findById(long id){
        return customerRepository.findById(id).orElse(null);
    }
}
