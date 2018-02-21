package com.mbronshteyn.gameserver.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.gameserver.data.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByDistributor_id(Long distributorId);	

}
