package com.mbronshteyn.gameserver.dto;

import com.mbronshteyn.gameserver.data.Vendor;

public class VendorDto {
	
	private Long distributorId;
	private String userName;	
	private String password;
	private Vendor vendor;
	
	public Long getDistributorId() {
		return distributorId;
	}
	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
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
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	
	

}
