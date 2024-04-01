package com.jsh.rocco.repositories;

import java.util.Optional;

import com.jsh.rocco.domains.entities.Payment;
import com.jsh.rocco.domains.entities.Reservation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;



public interface PaymentRepository extends CrudRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p WHRE Payment.reservtion = ?1")
	Optional<Payment> findByReservation(Reservation reservation);
}
