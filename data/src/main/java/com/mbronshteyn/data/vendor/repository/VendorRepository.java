package com.mbronshteyn.data.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.data.vendor.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	
	List<Vendor> findByDistributor_id(Long distributorId);		

}
