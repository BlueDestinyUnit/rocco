package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.Receipt;
import org.springframework.data.repository.CrudRepository;



public interface RecepitRepository extends CrudRepository<Receipt, Long> {

}
