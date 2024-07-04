package com.app.service;

import java.util.List;

import com.app.dto.Servicedto;

public interface ServingService {

	List<Servicedto> getAllServices();

	Servicedto getServiceById(Long serviceid);

	Servicedto addservice(Servicedto servicedto);

	Servicedto updateService(Servicedto servicedto, Long id);

	void deleteService(Long id);

}
