package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.dto.AppointmentRequestdto;
import com.app.dto.AppointmentResponsedto;
import com.app.dto.CustomerAppointmentResponsedto;
import com.app.dto.CustomerLogindto;
import com.app.dto.Customerdto;
import com.app.dto.Packagedto;
import com.app.dto.Servicedto;
import com.app.pojos.Appointment;
import com.app.service.AppointmentService;
import com.app.service.CustomerService;
import com.app.service.PackageService;
import com.app.service.ServingService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService custServ;

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private PackageService packageservice;
	@Autowired
	private ServingService servingservice;

	public CustomerController() {
		super();
	}

	@PostMapping("/register")
	public ResponseEntity<Customerdto> createCustomer(@Valid @RequestBody Customerdto custdto) {
		Customerdto customerdto = custServ.addCustomer(custdto);
		return new ResponseEntity<Customerdto>(customerdto, HttpStatus.CREATED);
	}

	@GetMapping("/login")
	public ResponseEntity<Customerdto> Login(@RequestParam String email, @RequestParam String password) {
		Customerdto custdto = custServ.ValidateCustomer(email, password);
		return new ResponseEntity<Customerdto>(custdto, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Customerdto>> getallCustomers() {
		return ResponseEntity.ok(custServ.getallcustomer());
	}

	@PutMapping("/{CustId}")
	public ResponseEntity<Customerdto> updateCustomer(@Valid @RequestBody Customerdto custdto,
			@PathVariable Long CustId) {
		Customerdto updatedcust = custServ.updateUser(custdto, CustId);
		return ResponseEntity.ok(updatedcust);
	}

	@DeleteMapping("/{CustId}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable("CustId") Long cid) {
		custServ.deleteUser(cid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Customer deleted successfully", true), HttpStatus.OK);

	}

	@GetMapping("/packages")
	public ResponseEntity<List<Packagedto>> getAllPackages() {
		return ResponseEntity.ok(packageservice.getAllPackages());

	}

	@GetMapping("/services")
	public ResponseEntity<List<Servicedto>> getAllServices() {
		return ResponseEntity.ok(servingservice.getAllServices());
	}

	@PostMapping("/bookappointment/{CustomerId}")
	public ResponseEntity<?> createAppointment(@PathVariable Long CustomerId,
			@RequestBody AppointmentRequestdto appointmentRequest) {
		System.out.println(appointmentRequest);
		try {
			AppointmentResponsedto appointmentresponse = appointmentService.bookAppointment(CustomerId,
					appointmentRequest);
			return ResponseEntity.ok(appointmentresponse);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/bookedappointments/{CustomerId}")
	public ResponseEntity<List<AppointmentResponsedto>>getAllAppointments(@PathVariable Long CustomerId) {
		return ResponseEntity.ok(custServ.bookedAppointments(CustomerId));
	}

}
