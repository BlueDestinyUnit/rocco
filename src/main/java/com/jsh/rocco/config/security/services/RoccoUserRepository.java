package com.jsh.rocco.config.security.services;

import com.jsh.rocco.config.security.domains.RoccoUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoccoUserRepository extends CrudRepository<RoccoUser,Long> {

    @Query("SELECT u FROM RoccoUser u WHERE u.email = ?1")
    Optional<RoccoUser> findUserByEmail(String email);
}
