package com.jsh.rocco.services;

import com.jsh.rocco.domains.entities.Address;
import com.jsh.rocco.domains.entities.Customer;
import com.jsh.rocco.repositories.AddressRepository;
import com.jsh.rocco.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;

    public void addAddress(Address address){
        addressRepository.save(address);
    }
    public void addCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
