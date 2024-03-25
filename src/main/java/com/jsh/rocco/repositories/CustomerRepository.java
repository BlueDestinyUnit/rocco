package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
