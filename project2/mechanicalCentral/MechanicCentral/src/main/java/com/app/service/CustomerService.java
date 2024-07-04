package com.app.service;

import java.util.List;

import com.app.dto.AppointmentResponsedto;
import com.app.dto.Customerdto;
import com.app.pojos.Appointment;

public interface CustomerService {
	List<Customerdto> getallcustomer();

	Customerdto getCustomerbyId(Long id);

	Customerdto addCustomer(Customerdto newcust);

	Customerdto updateUser(Customerdto cust, Long custId);

	void deleteUser(Long custId);

	Customerdto ValidateCustomer(String email, String password);
	
	List<AppointmentResponsedto> bookedAppointments(Long custId);

}
