package com.mbronshteyn.gameserver.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.gameserver.data.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
	
	List<Vendor> findByDistributor_id(Long distributorId);		

}
