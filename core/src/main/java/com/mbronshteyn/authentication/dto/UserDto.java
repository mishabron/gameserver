package com.mbronshteyn.authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mbronshteyn.authentication.model.UserRoles;

public class UserDto {
	
	private String userId;
	private String email;
	private String password;
	private UserRoles role;
	
    @JsonIgnore 	
	private String updateBy;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}	

	
}
