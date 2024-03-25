package com.jsh.rocco.repositories;

import com.jsh.rocco.domains.entities.RoccoUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoccoUserRepository extends CrudRepository<RoccoUser,String> {

    @Query("SELECT u FROM RoccoUser u WHERE u.email = ?1")
    Optional<RoccoUser> findUserByEmail(String email);
}
