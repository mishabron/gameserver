package com.mbronshteyn.gameserver.services;


import java.util.List;

import com.mbronshteyn.gameserver.data.Contact;
import com.mbronshteyn.gameserver.data.Distributor;
import com.mbronshteyn.gameserver.data.Vendor;
import com.mbronshteyn.gameserver.dto.ContactDto;
import com.mbronshteyn.gameserver.dto.DistributorDto;
import com.mbronshteyn.gameserver.dto.VendorDto;
import com.mbronshteyn.gameserver.exception.GameServerException;


public interface DistributorService {

	Distributor getDistributor(Long i);
	
	List<Distributor> getDistributors();

	Distributor addDistributor(DistributorDto distributor,String jwt) throws GameServerException;

	List<Contact> getContacts(Long distributorId);

	Contact addContact(ContactDto contact,String jwt) throws GameServerException;	
	
	List<Vendor> getVendors(Long distributorId);

	Vendor addVendor(VendorDto vendor, String jwt) throws GameServerException;		

}