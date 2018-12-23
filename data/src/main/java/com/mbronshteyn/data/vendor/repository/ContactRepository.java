package com.mbronshteyn.data.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.data.vendor.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByDistributor_id(Long distributorId);	

}
