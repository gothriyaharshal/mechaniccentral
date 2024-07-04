package com.app.serviceimpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.dto.AppointmentRequestdto;
import com.app.dto.AppointmentResponseWithPagingAndSorting;
import com.app.dto.AppointmentResponsedto;
import com.app.dto.Garagedto;
import com.app.exceptions.ResourceNotFoundException;
import com.app.pojos.Appointment;
import com.app.pojos.AppointmentRequest;
import com.app.pojos.AppointmentStatus;
import com.app.pojos.Garage;
import com.app.pojos.Packages;
import com.app.pojos.ServiceType;
import com.app.pojos.Services;
import com.app.repository.AppointmentRepo;
import com.app.repository.AppointmentRequestrepo;
import com.app.repository.CustomerRepo;
import com.app.repository.GarageRepo;
import com.app.repository.PackageRepo;
import com.app.repository.ServiceRepo;
import com.app.service.GarageService;

@Service
@Transactional
public class GarageServiceImpl implements GarageService {

	@Autowired
	private GarageRepo garagerepo;

	@Autowired
	private ModelMapper modelmapper;

	
	@Autowired
	private AppointmentRepo appointmentrepo;

	@Autowired
	private AppointmentRequestrepo appointmentreqrepo;

	@Override
	public List<Garagedto> getAllGarage() {

		List<Garage> garages = garagerepo.findAll();
		List<Garagedto> gargsdto = garages.stream().map(garage -> GarageTodto(garage)).collect(Collectors.toList());
		return gargsdto;

	}

	@Override
	public Garagedto getGarageById(Long id) {
		Garage garg = garagerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Garage", "id", id));

		return GarageTodto(garg);
	}

	@Override
	public Garagedto addGarage(Garagedto gargdto) {
		Garage garg = dtoToGarage(gargdto);
		Garage savedGarage = garagerepo.save(garg);
		return GarageTodto(savedGarage);
	}

	@Override
	public Garagedto updateGarage(Garagedto gargdto, Long gargId) {
		Garage garg = garagerepo.findById(gargId)
				.orElseThrow(() -> new ResourceNotFoundException("Garage", "id", gargId));

		garg.setName(gargdto.getName());
		garg.setEmail(gargdto.getEmail());
		garg.setMobile(gargdto.getMobile());
		garg.setPassword(garg.getPassword());
		garg.setAddress(gargdto.getAddress());
		Garage updatedgarg = garagerepo.save(garg);
		return GarageTodto(updatedgarg);
	}

	@Override
	public void deleteGarage(Long id) {
		Garage garg = garagerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Garage", "id", id));
		garagerepo.delete(garg);

	}

	private Garage dtoToGarage(Garagedto gargdto) {
		Garage garg = modelmapper.map(gargdto, Garage.class);
		return garg;
	}

	private Garagedto GarageTodto(Garage garg) {
		Garagedto gargdto = modelmapper.map(garg, Garagedto.class);
		return gargdto;
	}

	@Override
	public void AcceptAppointment(Long gargId, Long appointmentreqId) {

		AppointmentRequest appointmentreq = appointmentreqrepo.findById(appointmentreqId)
				.orElseThrow(() -> new ResourceNotFoundException("appointmentRequestId", "id", appointmentreqId));
		Garage garg = garagerepo.findById(gargId)
				.orElseThrow(() -> new ResourceNotFoundException("Garage", "id", gargId));

		Appointment appointment = new Appointment();
		appointment.setCustomer(appointmentreq.getCustomer());
		appointment.setStatus(AppointmentStatus.CONFIRMED);
		appointment.setType(appointmentreq.getType());
		// set date

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formattedDate = currentDate.format(formatter);
		appointment.setDate(formattedDate);
		// set time
		LocalTime currentTime = LocalTime.now();
		DateTimeFormatter formating = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formattedTime = currentTime.format(formating);
		appointment.setTime(formattedTime);

		// Determine which service type the appointmentreq request is for

		if (appointmentreq.getType() == ServiceType.SERVICES) {
			// Find the requested services by ID and add them to the appointmentreq
			Set<Services> services = new HashSet<>();
			for (Services service : appointmentreq.getServices()) {
				services.add(service);
			}
			appointment.setServices(services);
		} else if (appointmentreq.getType() == ServiceType.PACKAGE) {
			// Find the requested package by ID and add it to the appointmentreq
			Packages packages = appointmentreq.getPackagee();

			appointment.setPackagee(packages);
		} else if (appointmentreq.getType() == ServiceType.ONSPOTMECHANIC) {

			appointment.setOnSpotMechanic(appointmentreq.isOnSpotMechanic());
		}
		// Save the appointmentreq to the database
		appointment.setGarage(garg);
		appointmentrepo.save(appointment);
		garg.getAppointments().add(appointment);
		garagerepo.save(garg);
		appointmentreqrepo.delete(appointmentreq);

//		List<Garage> garages = garagerepo.findAll();
//		for (Garage garage : garages) {
//
//			if (garage.getAppointmentRequests()
//					.contains(modelmapper.map(appointmentreq, AppointmentRequest.class)) == true) {
//				garage.getAppointmentRequests().remove(modelmapper.map(appointmentreq, AppointmentRequest.class));
//				appointmentreqrepo.deleteById(gargId);
//				garagerepo.save(garage);
//			}
//		}

	}

	@Override
	public void DeclineAppointment(Long gargId, AppointmentRequestdto appointmentreq) {
		Garage garg = garagerepo.findById(gargId)
				.orElseThrow(() -> new ResourceNotFoundException("Garage", "id", gargId));
		garg.getAppointmentRequests().remove(modelmapper.map(appointmentreq, AppointmentRequest.class));

	}

	@Override
	public List<AppointmentResponsedto> getAllRequestAppointment(Long gargId) {
		Garage garg = garagerepo.findById(gargId)
				.orElseThrow(() -> new ResourceNotFoundException("Garage", "id", gargId));
		List<AppointmentRequest> request = garg.getAppointmentRequests();
		System.out.println(request);

		List<AppointmentResponsedto> appointmentResponseDtos = new ArrayList<>();

		for (AppointmentRequest appointmentRequest : request) {
			AppointmentResponsedto appointmentResponseDto = modelmapper.map(appointmentRequest,
					AppointmentResponsedto.class);
			appointmentResponseDto.setCustomerId(appointmentRequest.getCustomer().getId());
			appointmentResponseDto.setCustomername(appointmentRequest.getCustomer().getName());
			appointmentResponseDto.setGarageId(appointmentRequest.getGarage().getId());
			appointmentResponseDto.setGaragename(appointmentRequest.getGarage().getName());
			appointmentResponseDto.setGarageaddress(appointmentRequest.getGarage().getAddress());
			appointmentResponseDto.setGaragemob(appointmentRequest.getGarage().getMobile());
			if (appointmentRequest.getPackagee() != null) {
				appointmentResponseDto.setPackage_name(appointmentRequest.getPackagee().getName());
			} else if (appointmentRequest.getServices() != null && !appointmentRequest.getServices().isEmpty()) {
				Set<String> servicenames = new HashSet<>();
				for (Services service : appointmentRequest.getServices()) {
					servicenames.add(service.getName());
				}
				appointmentResponseDto.setServices(servicenames);
			} else {
				appointmentResponseDto.setOnSpotMechanic(appointmentRequest.isOnSpotMechanic());
			}
			appointmentResponseDtos.add(appointmentResponseDto);
		}

		return appointmentResponseDtos;
	}

	@Override
	public AppointmentResponseWithPagingAndSorting getAllBookedAppointments(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDirc) {
//		Sort sort = null;
//		if (sortDirc.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		} else {
//			sort = Sort.by(sortBy).descending();
//		}
		// using ternary operator instead of if-else
		Sort sort = (sortDirc.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
//		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy)); // this was for when we doing static direction for sorting
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
//		Sort.by(sortBy).ascending() or Sort.by(sortBy).descending() like this by using method chaining we can sort by ascending or desceding also
		// but instead of doing this, we are doing it dynamically and taking an another
		// parameter from user to sorting in which direction
		// we are using Sort.by() method which sort by String properties, we can also
		// use other overloaded methods to sort according to our needs

		Page<Appointment> Appointmentpage = appointmentrepo.findAll(p); // this will return appointments in pages.
		List<Appointment> allAppointments = Appointmentpage.getContent();

		List<AppointmentResponsedto> appointmentResponseDtos = new ArrayList<AppointmentResponsedto>();

		for (Appointment appointment : allAppointments) {
			AppointmentResponsedto appointmentResponseDto = modelmapper.map(appointment, AppointmentResponsedto.class);
			appointmentResponseDto.setCustomerId(appointment.getCustomer().getId());
			appointmentResponseDto.setCustomername(appointment.getCustomer().getName());
			appointmentResponseDto.setGarageId(appointment.getGarage().getId());
			appointmentResponseDto.setGaragename(appointment.getGarage().getName());
			appointmentResponseDto.setGarageaddress(appointment.getGarage().getAddress());
			appointmentResponseDto.setGaragemob(appointment.getGarage().getMobile());
			if (appointment.getPackagee() != null) {
				appointmentResponseDto.setPackage_name(appointment.getPackagee().getName());
			}
			Set<Services> services = appointment.getServices();
			if (services != null && !services.isEmpty()) {
				Set<String> servicenames = new HashSet<>();
				for (Services service : services) {
					servicenames.add(service.getName());
				}
				appointmentResponseDto.setServices(servicenames);
			}
			appointmentResponseDtos.add(appointmentResponseDto);
		}
		AppointmentResponseWithPagingAndSorting response = new AppointmentResponseWithPagingAndSorting();
		response.setContent(appointmentResponseDtos);
		response.setPageNumber(Appointmentpage.getNumber());
		response.setPageSize(Appointmentpage.getSize());
		response.setTotalPages(Appointmentpage.getTotalPages());
		response.setTotalElements(Appointmentpage.getTotalElements());
		response.setLastPage(Appointmentpage.isLast());

		return response;
	}

}
