package com.jsh.rocco.repositories;

import java.util.List;
import java.util.Optional;

import com.jsh.rocco.domains.entities.Payment;
import com.jsh.rocco.domains.entities.Reservation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;



public interface PaymentRepository extends CrudRepository<Payment, Long> {
	@Query("SELECT p FROM Payment p WHERE Payment.reservtion = ?1")
	Optional<Payment> findByReservation(Reservation reservation);

	@Query("SELECT p FROM Payment p ORDER BY p.id DESC LIMIT 1")
	Optional<Payment> findPayment();


//	@Query("SELECT p.paymentNumber FROM Payment p WHERE SUBSTRING(p.paymentNumber, 2, 9) = ?1 ORDER BY p.paymentNumber DESC")
//	List<String> findOrderNumbersByPaymentNumber(String currentDate);


}
