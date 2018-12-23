package com.mbronshteyn.authentication.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mbronshteyn.data.authentication.User;
import com.mbronshteyn.authentication.dto.UserDto;
import com.mbronshteyn.authentication.services.UserService;

@Component
@Path("/Authentication")
public class UserResource {
	
	@HeaderParam(HttpHeaders.AUTHORIZATION) 
	String jwt;
	
	@Autowired
	UserService userService;
	
    @POST
    @Path("/user/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"LOTOROLA_MANAGER"})
    public UserDto createUser(UserDto userDto) {
    	
    	DecodedJWT token = JWT.decode(jwt);
    	userDto.setUpdateBy(token.getSubject());
    	   	
    	User user = userService.addNewUser(userDto);
    	
    	UserDto returnUser = new UserDto();    	
    	returnUser.setUserId(user.getUserId());
    	returnUser.setRole(user.getRole());
    	returnUser.setEmail(user.getEmail());
    	
    	return returnUser;
    }

}
