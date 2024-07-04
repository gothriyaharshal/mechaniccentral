package com.app.service;

import java.util.List;

import com.app.dto.AppointmentRequestdto;
import com.app.dto.AppointmentResponsedto;
import com.app.dto.AppointmentResponsewithPaging;
import com.app.pojos.Appointment;
import com.app.pojos.AppointmentStatus;

public interface AppointmentService {

	AppointmentResponsedto bookAppointment(Long customerId, AppointmentRequestdto appointmentdto);

	AppointmentResponsewithPaging getAllAppointments(Integer pagenumber, Integer pagesize);

	AppointmentResponsedto getAppointmentById(Long appointmentId);

	AppointmentResponsedto updateAppointment(AppointmentRequestdto appointmentdto, Long appointmentId);

	void deleteAppointment(Long appointmentId);

	List<AppointmentResponsedto> getAppointmentsByGarage(Long garageId);

	List<AppointmentResponsedto> getAppointmentsByCustomer(Long customerId);

	List<Appointment> getAppointmentsByGarageAndStatus(Long garageId, AppointmentStatus status);

	List<Appointment> getAppointmentsByCustomerAndStatus(Long customerId, AppointmentStatus status);

//	List<Appointment> getConfirmedAppointments();
//
//	List<Appointment> getInProgressAppointments();
//
//	List<Appointment> getCancelledAppointments();

}
