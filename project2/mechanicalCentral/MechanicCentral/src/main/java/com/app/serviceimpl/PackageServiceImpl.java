package com.app.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.Packagedto;
import com.app.exceptions.ResourceNotFoundException;
import com.app.pojos.Packages;
import com.app.repository.PackageRepo;
import com.app.service.PackageService;

@Service
@Transactional
public class PackageServiceImpl implements PackageService {

	@Autowired
	private ModelMapper modelmapper;

	@Autowired
	private PackageRepo packagerepo;

	@Override
	public List<Packagedto> getAllPackages() {
		List<Packages> pkgs = packagerepo.findAll();
		List<Packagedto> pkgdto = pkgs.stream().map((pkg) -> PackageTodto(pkg)).collect(Collectors.toList());
		return pkgdto;
	}

	@Override
	public Packagedto getPackageById(Long packageId) {
		Packages pkg = packagerepo.findById(packageId)
				.orElseThrow(() -> new ResourceNotFoundException("Package", "id", packageId));
		return PackageTodto(pkg);
	}

	@Override
	public Packagedto addPackage(Packagedto packagedto) {
		Packages pkg = dtoToPackage(packagedto);
		Packages savedpkg = packagerepo.save(pkg);
		return PackageTodto(savedpkg);
	}

	@Override
	public Packagedto updatePackage(Packagedto packagedto, Long id) {
		Packages pkg = packagerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Package", "id", id));
		pkg.setName(packagedto.getName());
		pkg.setDescription(packagedto.getDescription());
		pkg.setPrice(packagedto.getPrice());
		Packages savedPkg = packagerepo.save(pkg);
		return PackageTodto(savedPkg);
	}

	@Override
	public void deletePackage(Long id) {
		Packages pkg = packagerepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Package", "id", id));
		packagerepo.delete(pkg);

	}

	private Packages dtoToPackage(Packagedto packagedto) {
		Packages pkg = modelmapper.map(packagedto, Packages.class);
		return pkg;
	}

	private Packagedto PackageTodto(Packages pkg) {
		Packagedto pkgdto = modelmapper.map(pkg, Packagedto.class);
		return pkgdto;
	}

}
