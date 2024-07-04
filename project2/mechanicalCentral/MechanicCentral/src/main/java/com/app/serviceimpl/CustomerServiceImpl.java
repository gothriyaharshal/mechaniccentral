package com.app.serviceimpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.AppointmentResponsedto;
import com.app.dto.Customerdto;
import com.app.exceptions.ResourceNotFoundException;
import com.app.pojos.Appointment;
import com.app.pojos.AppointmentRequest;
import com.app.pojos.Customer;
import com.app.pojos.Garage;
import com.app.pojos.Services;
import com.app.repository.CustomerRepo;
import com.app.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo custrepo;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public List<Customerdto> getallcustomer() {
		List<Customer> customers = custrepo.findAll();
		List<Customerdto> custdto = customers.stream().map(customer -> customertodto(customer))
				.collect(Collectors.toList());
		return custdto;
	}

	@Override
	public Customerdto addCustomer(Customerdto custdto) {
		Customer cust = this.dtotocustomer(custdto);
		Customer savedCustomer = this.custrepo.save(cust);
		Customerdto customerdto = this.customertodto(savedCustomer);
		return customerdto;
	}

	@Override
	public Customerdto getCustomerbyId(Long id) {
		Customer cust = custrepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
		return customertodto(cust);
	}

	@Override
	public Customerdto updateUser(Customerdto custdto, Long custId) {
		Customer cust = custrepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", custId));

		cust.setName(custdto.getName());
		cust.setEmail(custdto.getEmail());
		cust.setMobile(custdto.getMobile());
		cust.setPassword(custdto.getPassword());

		Customer updatedcustomer = custrepo.save(cust);
		return customertodto(updatedcustomer);

	}

	@Override
	public void deleteUser(Long CustId) {
		Customer cust = custrepo.findById(CustId)
				.orElseThrow(() -> new ResourceNotFoundException("Customer", "Id", CustId));
		custrepo.delete(cust);

	}

	private Customer dtotocustomer(Customerdto custdto) {
		Customer cust = modelmapper.map(custdto, Customer.class);
		return cust;

	}

	private Customerdto customertodto(Customer cust) {
		Customerdto custdto = modelmapper.map(cust, Customerdto.class);
		return custdto;

	}

	@Override
	public Customerdto ValidateCustomer(String email, String password) {

		Customer customer = custrepo.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Email or password", null, 0));
		return modelmapper.map(customer, Customerdto.class);

	}

	@Override
	public List<AppointmentResponsedto> bookedAppointments(Long custId) {
		Customer customer = custrepo.findById(custId)
				.orElseThrow(() -> new ResourceNotFoundException("customer not found", "custId", custId));
		List<Appointment> allbookedappointments = customer.getAppointments();
		
////		
//		AppointmentResponsedto app = new AppointmentResponsedto();
//		app.setAppointmentstatus(null);
//
//		 modelmapper.map(allbookedappointments, AppointmentResponsedto.class);
		List<AppointmentResponsedto> custdto = allbookedappointments.stream().map(appointment -> modelmapper.map(appointment, AppointmentResponsedto.class))
				.collect(Collectors.toList());
		return custdto;
//		Customer customer = custrepo.findById(custId)
//				.orElseThrow(() -> new ResourceNotFoundException("Customer", "id", custId));
//		List<Appointment> request = customer.getAppointments();
////		System.out.println(request);
//		List<AppointmentResponsedto> appointments = modelmapper.map(request, AppointmentResponsedto.class);
//		return appointments;

//		List<AppointmentResponsedto> appointmentResponseDtos = new ArrayList<>();
//
//		for (Appointment appointmentRequest : request) {
//			AppointmentResponsedto appointmentResponseDto = modelmapper.map(appointmentRequest,
//					AppointmentResponsedto.class);
//			appointmentResponseDto.setCustomerId(appointmentRequest.getCustomer().getId());
//			appointmentResponseDto.setCustomername(appointmentRequest.getCustomer().getName());
//			appointmentResponseDto.setGarageId(appointmentRequest.getGarage().getId());
//			appointmentResponseDto.setGaragename(appointmentRequest.getGarage().getName());
//			appointmentResponseDto.setGarageaddress(appointmentRequest.getGarage().getAddress());
//			appointmentResponseDto.setGaragemob(appointmentRequest.getGarage().getMobile());
//			if (appointmentRequest.getPackagee() != null) {
//				appointmentResponseDto.setPackage_name(appointmentRequest.getPackagee().getName());
//			} else if (appointmentRequest.getServices() != null && !appointmentRequest.getServices().isEmpty()) {
//				Set<String> servicenames = new HashSet<>();
//				for (Services service : appointmentRequest.getServices()) {
//					servicenames.add(service.getName());
//				}
//				appointmentResponseDto.setServices(servicenames);
//			} else {
//				appointmentResponseDto.setOnSpotMechanic(appointmentRequest.isOnSpotMechanic());
//			}
//			appointmentResponseDtos.add(appointmentResponseDto);
//		}
//
//		return appointmentResponseDtos;
		
	}

//	private Customer dtotocustomer(Customerdto custdto) {
//		Customer cust = new Customer();
//		cust.setId(custdto.getId());
//		cust.setName(custdto.getName());
//		cust.setEmail(custdto.getEmail());
//		cust.setMobile(custdto.getMobile());
//		cust.setPassword(custdto.getPassword());
//		return cust;
//
//	}
//
//	private Customerdto customertodto(Customer cust) {
//		Customerdto custdto = new Customerdto();
//		custdto.setId(cust.getId());
//		custdto.setName(cust.getName());
//		custdto.setEmail(cust.getEmail());
//		custdto.setMobile(cust.getMobile());
//		custdto.setPassword(cust.getPassword());
//		return custdto;
//
//	}

}
