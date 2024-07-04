package com.app.service;

import java.util.List;

import com.app.dto.Packagedto;

public interface PackageService {
	
	List<Packagedto> getAllPackages();

	Packagedto getPackageById(Long packageId);

	Packagedto addPackage(Packagedto packagedto);

	Packagedto updatePackage(Packagedto packagedto, Long id);

	void deletePackage(Long id);


}
