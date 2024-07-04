package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
	 Optional<Customer> findByEmailAndPassword(String email, String password);

}
