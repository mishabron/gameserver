package com.mbronshteyn.gameserver.services.impl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.client.Client;
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
import com.mbronshteyn.authentication.security.ssl.JerseyWithSSL;
import com.mbronshteyn.data.vendor.Contact;
import com.mbronshteyn.data.vendor.Distributor;
import com.mbronshteyn.data.vendor.Vendor;
import com.mbronshteyn.data.vendor.repository.ContactRepository;
import com.mbronshteyn.data.vendor.repository.DistributorRepository;
import com.mbronshteyn.data.vendor.repository.VendorRepository;
import com.mbronshteyn.gameserver.dto.ContactDto;
import com.mbronshteyn.gameserver.dto.DistributorDto;
import com.mbronshteyn.gameserver.dto.VendorDto;
import com.mbronshteyn.gameserver.exception.GameServerException;
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
	public Distributor addDistributor(DistributorDto distributorDto,String jwt) throws GameServerException {

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
	public Contact addContact(ContactDto contactDto,String jwt) throws GameServerException {
		
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
	@Transactional(rollbackFor = Exception.class)	
	public Vendor addVendor(VendorDto vendorDto,String jwt) throws GameServerException {

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


	private void createUser(UserDto user, String jwt) throws GameServerException {
				
		JerseyWithSSL sslClientBuilder = new JerseyWithSSL();
		Client client = null;
		try {
			client = sslClientBuilder.initClient();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new GameServerException(e.getMessage());
		}	
		
		Response response = client.target(AUTH_URI).request(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, jwt).post(Entity.entity(user, MediaType.APPLICATION_JSON));
		if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			throw new GameServerException("Exception creating user ",response.getStatus());			
		}		
	}

}
