package com.mbronshteyn.gameserver.services.impl;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mbronshteyn.authentication.dto.UserDto;
import com.mbronshteyn.authentication.model.UserRoles;
import com.mbronshteyn.gameserver.data.Contact;
import com.mbronshteyn.gameserver.data.Distributor;
import com.mbronshteyn.gameserver.data.Vendor;
import com.mbronshteyn.gameserver.data.repository.ContactRepository;
import com.mbronshteyn.gameserver.data.repository.DistributorRepository;
import com.mbronshteyn.gameserver.data.repository.VendorRepository;
import com.mbronshteyn.gameserver.dto.ContactDto;
import com.mbronshteyn.gameserver.dto.DistributorDto;
import com.mbronshteyn.gameserver.dto.VendorDto;
import com.mbronshteyn.gameserver.services.DistributorService;

@Service
public class DistributorServiceImpl implements  DistributorService{
	
	@Autowired
	DistributorRepository distributorRepository;
	
	@Autowired	
	ContactRepository contactRepository;
		
	@Autowired	
	VendorRepository vendorRepository;
	
	@Value("${auth.service.newuser.uri}")
	private String AUTH_URI;
	

	@Override
	public Distributor getDistributor(Long i) {
		
		Distributor distributor = distributorRepository.findOne(i);
		
		return distributor;
	}

	@Override
	public List<Distributor> getDistributors() {
		// 
		return distributorRepository.findAll();
	}

	@Override
	@Transactional		
	public Distributor addDistributor(DistributorDto distributorDto,String jwt) {

		Distributor distributor = distributorDto.getDistributor();
		distributor.setId(null);
		Contact contact = distributor.getContacts().get(0);
		
		Distributor newDist = distributorRepository.save(distributor);
		
		UserDto user = new UserDto();
		user.setEmail(contact.getEmail());
		user.setUserId(distributorDto.getUserName());
		user.setPassword(distributorDto.getPassword());
		user.setRole(UserRoles.DISTRIBUTOR);
		//user.setUpdateBy(updateBy);
		
		createUser(user,jwt);
		
		return newDist;
	}

	@Override
	public List<Contact> getContacts(Long distributorId) {

		return contactRepository.findByDistributor_id(distributorId);
	}

	@Override
	@Transactional	
	public Contact addContact(ContactDto contactDto,String jwt) {
		
		Contact contact = contactDto.getContact();
		contact.setId(null);
		
		Distributor distributor = distributorRepository.findOne(contactDto.getDistributorId());		
		distributor.addContact(contact);
		distributorRepository.save(distributor);	
		
		UserDto user = new UserDto();
		user.setEmail(contact.getEmail());
		user.setUserId(contactDto.getUserName());
		user.setPassword(contactDto.getPassword());
		user.setRole(UserRoles.DISTRIBUTOR);

		createUser(user,jwt);
		
		return contact;
	}

	@Override
	public List<Vendor> getVendors(Long distributorId) {

		return vendorRepository.findByDistributor_id(distributorId);
	}

	@Override
	@Transactional	
	public Vendor addVendor(VendorDto vendorDto,String jwt) {

		Vendor vendor = vendorDto.getVendor();
		vendor.setId(null);
		
		Distributor distributor = distributorRepository.findOne(vendorDto.getDistributorId());		
		distributor.addVendor(vendor);
		distributorRepository.save(distributor);	
		
		UserDto user = new UserDto();
		user.setEmail(vendor.getEmail());
		user.setUserId(vendorDto.getUserName());
		user.setPassword(vendorDto.getPassword());
		user.setRole(UserRoles.DISTRIBUTOR);

		createUser(user,jwt);
		
		return vendor;		
	}


	private void createUser(UserDto user, String jwt) {
		
		Client client = ClientBuilder.newClient();		
		Response response = client.target(AUTH_URI).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwt).post(Entity.entity(user, MediaType.APPLICATION_JSON));
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			throw new RuntimeException("Exception creating user ");			
		}		
	}

}
