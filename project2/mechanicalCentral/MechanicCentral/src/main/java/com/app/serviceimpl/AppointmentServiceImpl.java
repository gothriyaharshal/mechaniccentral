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
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import com.app.dto.AppointmentRequestdto;
import com.app.dto.AppointmentResponsedto;
import com.app.dto.AppointmentResponsewithPaging;
import com.app.exceptions.ResourceNotFoundException;
import com.app.pojos.Appointment;
import com.app.pojos.AppointmentRequest;
import com.app.pojos.AppointmentStatus;
import com.app.pojos.Customer;
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
import com.app.service.AppointmentService;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRequestrepo appointmentreqrepo;

	@Autowired
	private AppointmentRepo appointmentrepo;
	@Autowired
	private CustomerRepo customerrepo;

	@Autowired
	private GarageRepo garagerepo;

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private ServiceRepo servicerepo;
	@Autowired
	private PackageRepo packagerepo;

	@Override
	public AppointmentResponsedto bookAppointment(Long customerId, AppointmentRequestdto appointmentreqdto) {

		Customer customer = customerrepo.findById(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
		List<Garage> garages = garagerepo.findAll();

		for (Garage garage : garages) {
			AppointmentRequest appointmentreq = new AppointmentRequest();
			appointmentreq.setCustomer(customer);
			appointmentreq.setStatus(AppointmentStatus.PENDING);
			appointmentreq.setType(appointmentreqdto.getServicetype());
			// set date
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = currentDate.format(formatter);
			appointmentreq.setDate(formattedDate);
			// set time
			LocalTime currentTime = LocalTime.now();
			DateTimeFormatter formating = DateTimeFormatter.ofPattern("HH:mm:ss");
			String formattedTime = currentTime.format(formating);
			appointmentreq.setTime(formattedTime);

			// Determine which service type the appointmentreq request is for

			if (appointmentreqdto.getServicetype() == ServiceType.SERVICES) {
				// Find the requested services by ID and add them to the appointmentreq
				Set<Services> services = new HashSet<>();
				for (Long serviceId : appointmentreqdto.getServiceIds()) {
					Services service = servicerepo.findById(serviceId)
							.orElseThrow(() -> new ResourceNotFoundException("Service", "id", serviceId));
					services.add(service);

				}
				appointmentreq.setServices(services);
			} else if (appointmentreqdto.getServicetype() == ServiceType.PACKAGE) {
				// Find the requested package by ID and add it to the appointmentreq
				Packages packages = packagerepo.findById(appointmentreqdto.getPackageId()).orElseThrow(
						() -> new ResourceNotFoundException("Package", "id", appointmentreqdto.getPackageId()));
				appointmentreq.setPackagee(packages);
			} else if (appointmentreqdto.getServicetype() == ServiceType.ONSPOTMECHANIC) {
				appointmentreq.setOnSpotMechanic(appointmentreqdto.isOnSpotMechanic());
			}
			appointmentreq.setGarage(garage);
			// set other properties of appointmentRequest
			garage.getAppointmentRequests().add(appointmentreq);
			garagerepo.save(garage);
//		    appointmentreqrepo.save(appointmentreq);
		}

		return null;
	}

	@Override

	public AppointmentResponsewithPaging getAllAppointments(Integer pagenumber, Integer pagesize) {
		// here using pagination and sorting which is provided by our JpaRespository
		// because
		// it extends interface PagingAndSortingRepository<T, ID> which extends
		// interface CrudRepository<T, ID>

		// in this we have to provide pagesize and pagenumber which are send by the ui
		// part.
//		pazesize: size of page i.e., number of results to be shown on a page  
//		pagenumber: data of which page number to be displayed

		Pageable p = PageRequest.of(pagenumber, pagesize);

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
		AppointmentResponsewithPaging response = new AppointmentResponsewithPaging();
		response.setContent(appointmentResponseDtos);
		response.setPageNumber(Appointmentpage.getNumber());
		response.setPageSize(Appointmentpage.getSize());
		response.setTotalPages(Appointmentpage.getTotalPages());
		response.setTotalElements(Appointmentpage.getTotalElements());
		response.setLastPage(Appointmentpage.isLast());

		return response;
	}

	@Override
	public AppointmentResponsedto getAppointmentById(Long appointmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppointmentResponsedto updateAppointment(AppointmentRequestdto appointmentdto, Long appointmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAppointment(Long appointmentId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AppointmentResponsedto> getAppointmentsByGarage(Long garageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppointmentResponsedto> getAppointmentsByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> getAppointmentsByGarageAndStatus(Long garageId, AppointmentStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Appointment> getAppointmentsByCustomerAndStatus(Long customerId, AppointmentStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<appointmentreq> getConfirmedappointmentreqs() {
//		return appointmentreqrepo.findBystatusCONFIRMED();
//	}
//
//	@Override
//	public List<appointmentreq> getInProgressappointmentreqs() {
//
//		return appointmentreqrepo.findBystatusINPROGRESS();
//	}

}
