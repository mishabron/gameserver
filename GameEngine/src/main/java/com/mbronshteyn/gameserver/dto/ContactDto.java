package com.mbronshteyn.gameserver.dto;

import com.mbronshteyn.data.vendor.Contact;

public class ContactDto {
	
	private Long distributorId;
	private String userName;	
	private String password;
	private Contact contact;
	
	public Long getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
}
