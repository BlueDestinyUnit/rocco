package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
