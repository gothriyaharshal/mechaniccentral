package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.applicationConfig.ApplicationConstants;
import com.app.dto.AppointmentRequestdto;
import com.app.dto.AppointmentResponseWithPagingAndSorting;
import com.app.dto.AppointmentResponsedto;
import com.app.dto.Garagedto;
import com.app.pojos.Garage;
import com.app.service.GarageService;

@RestController
@RequestMapping("/garage")
public class GarageController {

	@Autowired
	private GarageService garageservice;

	public GarageController() {
		super();
	}

	@PostMapping("/accept/{garageId}/appointmentreqId/{appreqId}")
	public ResponseEntity<AppointmentResponsedto> AcceptAppointment(@PathVariable Long garageId,
			@PathVariable Long appreqId) {
		garageservice.AcceptAppointment(garageId, appreqId);
		return null;

	}

	@PutMapping("/decline/{garageId}")
	public ResponseEntity<AppointmentResponsedto> DeclineAppointment(@PathVariable Long garageId,
			@RequestBody AppointmentRequestdto appointreq) {
		garageservice.DeclineAppointment(garageId, appointreq);
		return null;

	}

	@GetMapping("/{garageId}")
	public ResponseEntity<Garagedto> getGarageById(@PathVariable Long garageId) {

		return ResponseEntity.ok(garageservice.getGarageById(garageId));
	}

	@GetMapping("/requestedappoinments/{garageId}")
	public ResponseEntity<List<AppointmentResponsedto>> getAllRequestedAppointments(@PathVariable Long garageId) {
		Garage garg = new Garage();
		System.out.println(garg.getAppointmentRequests());
		return ResponseEntity.ok(garageservice.getAllRequestAppointment(garageId));
	}

	@GetMapping("/bookedappointments")
	public ResponseEntity<AppointmentResponseWithPagingAndSorting> getAllAppointments(
			@RequestParam(value = "pageNumber", defaultValue = ApplicationConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = ApplicationConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = ApplicationConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value= "sortDirc", defaultValue = ApplicationConstants.SORT_DIRC, required=false) String sortDirc) {

		// http://localhost:8080/admin/appointments?pageNumber=0&pageSize=10&sortBy=title
		// fetching values from RequestParam and if not provided using default values
		// pageNumber is here in spring runs from 0, make note of it
		// and much needed information we should send with response that what is the
		// total number of pages we have and are you on last page or not such as like
		// pageNumber, pageSize, totalElements, totalPages, lastPage, content etc.
		return ResponseEntity.ok(garageservice.getAllBookedAppointments(pageNumber, pageSize, sortBy, sortDirc));

	}

}
