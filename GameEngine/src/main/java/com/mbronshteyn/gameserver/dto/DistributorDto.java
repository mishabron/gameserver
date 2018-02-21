package com.mbronshteyn.gameserver.dto;

import com.mbronshteyn.gameserver.data.Distributor;

public class DistributorDto {
	
	private String userName;	
	private String password;
	Distributor distributor;
	
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
	public Distributor getDistributor() {
		return distributor;
	}
	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}
	
	

}
