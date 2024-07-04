package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Appointment;
import com.app.pojos.AppointmentStatus;
import com.app.pojos.Customer;
import com.app.pojos.Garage;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

	// custom finder methods in repositories
	List<Appointment> findByCustomer(Customer customer);

	List<Appointment> findByGarage(Garage garage);

	List<Appointment> findByCustomerAndStatus(Customer customer, AppointmentStatus status);

	List<Appointment> findByGarageAndStatus(Garage garage, AppointmentStatus status);

//	List<Appointment> findBystatusCONFIRMED();
//
//	List<Appointment> findBystatusINPROGRESS();
//
//	List<Appointment> findBystatusCANCELLED();
}
