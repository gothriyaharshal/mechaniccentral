package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.applicationConfig.ApplicationConstants;
import com.app.dto.ApiResponse;
import com.app.dto.AppointmentResponsewithPaging;
import com.app.dto.Customerdto;
import com.app.dto.Garagedto;
import com.app.dto.Packagedto;
import com.app.dto.Servicedto;
import com.app.service.AppointmentService;
import com.app.service.CustomerService;
import com.app.service.GarageService;
import com.app.service.PackageService;
import com.app.service.ServingService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private PackageService packageservice;
	@Autowired
	private ServingService servingservice;
	@Autowired
	private GarageService garageservice;

	@Autowired
	private CustomerService customerservice;

	@Autowired
	private AppointmentService appointmentservice;

	AdminController() {
		super();
	}

	@PostMapping("/package")
	public ResponseEntity<Packagedto> AddPackage(@RequestBody Packagedto pkgdto) {
		Packagedto pkg = packageservice.addPackage(pkgdto);
		return new ResponseEntity<Packagedto>(pkg, HttpStatus.CREATED);

	}

	@GetMapping("/packages")
	public ResponseEntity<List<Packagedto>> getAllPackages() {
		return ResponseEntity.ok(packageservice.getAllPackages());

	}

	@GetMapping("/package/{packageId}")
	public ResponseEntity<Packagedto> getPackageById(@PathVariable Long packageId) {
		return ResponseEntity.ok(packageservice.getPackageById(packageId));

	}

	@PutMapping("/package/{packageId}")
	public ResponseEntity<Packagedto> updatePackage(@RequestBody Packagedto pkgdto, @PathVariable Long packageId) {

		return ResponseEntity.ok(packageservice.updatePackage(pkgdto, packageId));
	}

	@DeleteMapping("/package/{packageId}")
	public ResponseEntity<ApiResponse> deletePackage(@PathVariable Long id) {
		packageservice.deletePackage(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Package deleted successfully", true), HttpStatus.OK);

	}

	@PostMapping("/service")
	public ResponseEntity<Servicedto> addService(@RequestBody Servicedto servicedto) {

		Servicedto service = servingservice.addservice(servicedto);
		return new ResponseEntity<Servicedto>(service, HttpStatus.CREATED);

	}

	@GetMapping("/services")
	public ResponseEntity<List<Servicedto>> getAllServices() {
		return ResponseEntity.ok(servingservice.getAllServices());
	}

	@GetMapping("/service/{serviceId}")
	public ResponseEntity<Servicedto> getServiceById(@PathVariable Long serviceId) {
		return ResponseEntity.ok(servingservice.getServiceById(serviceId));
	}

	@PutMapping("/service/{serviceId}")
	public ResponseEntity<Servicedto> updateService(@RequestBody Servicedto servicedto, @PathVariable Long serviceId) {
		Servicedto updatedservice = servingservice.updateService(servicedto, serviceId);
		return ResponseEntity.ok(updatedservice);
	}

	@DeleteMapping("/service/{serviceId}")
	public ResponseEntity<ApiResponse> deleteService(@PathVariable Long serviceId) {
		servingservice.deleteService(serviceId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Service deleted successfully", true), HttpStatus.OK);
	}

	@PostMapping("/garage")
	public ResponseEntity<Garagedto> AddGarage(@RequestBody Garagedto gargdto) {
		Garagedto garage = garageservice.addGarage(gargdto);
		return new ResponseEntity<Garagedto>(garage, HttpStatus.CREATED);

	}

	@GetMapping("/garages")
	public ResponseEntity<List<Garagedto>> getAllGarage() {
		return ResponseEntity.ok(garageservice.getAllGarage());
	}

	@GetMapping("/garage/{garageId}")
	public ResponseEntity<Garagedto> getGarageById(@PathVariable Long garageId) {
		return ResponseEntity.ok(garageservice.getGarageById(garageId));
	}

	@PutMapping("/garage/{garageId}")
	public ResponseEntity<Garagedto> updateGarage(@RequestBody Garagedto garagedto, @PathVariable Long garageId) {
		return ResponseEntity.ok(garageservice.updateGarage(garagedto, garageId));
	}

	@DeleteMapping("/garage/{garageId}")
	public ResponseEntity<ApiResponse> deleteGarage(@PathVariable Long garageId) {
		garageservice.deleteGarage(garageId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Garage deleted successfully", true), HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customerdto>> getallCustomers() {
		return ResponseEntity.ok(customerservice.getallcustomer());
	}

	@GetMapping("/customer/{CustId}")
	public ResponseEntity<Customerdto> getCustomerbyId(@PathVariable Long CustId) {
		return ResponseEntity.ok(customerservice.getCustomerbyId(CustId));
	}

	@GetMapping("/appointments")
	public ResponseEntity<AppointmentResponsewithPaging> getAllAppointments(
			@RequestParam(value = "pageNumber", defaultValue = ApplicationConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.PAGE_SIZE, required = false) Integer pageSize) {

		// http://localhost:8080/admin/appointments?pageNumber=0&pageSize=10
		// fetching values from RequestParam and if not provided using default values
		// pageNumber is here in spring runs from 0, make note of it
		// and much needed information we should send with response that what is the
		// total number of pages we have and are you on last page or not such as like
		// pageNumber, pageSize, totalElements, totalPages, lastPage, content etc.
		return ResponseEntity.ok(appointmentservice.getAllAppointments(pageNumber, pageSize));
	}

}
