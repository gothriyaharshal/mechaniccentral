package com.app.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.dto.AppointmentResponsedto;
import com.app.repository.AppointmentRequestrepo;
import com.app.service.AppointmentRequestService;

public class AppointmentRequestServiceImpl implements AppointmentRequestService{

	@Autowired
	private AppointmentRequestrepo appointmentrequestrepo;
	@Override
	public AppointmentResponsedto getAllAppointment(Long gargeId) {
		
		return null;
	}

}
