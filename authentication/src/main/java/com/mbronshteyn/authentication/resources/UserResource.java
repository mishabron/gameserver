package com.mbronshteyn.authentication.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbronshteyn.authentication.data.User;
import com.mbronshteyn.authentication.dto.UserDto;
import com.mbronshteyn.authentication.services.UserService;

@Component
@Path("/Authentication")
public class UserResource {
	
	@Autowired
	UserService userService;
	
    @POST
    @Path("/user/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public UserDto createUser(UserDto userDto) {
    	
    	User user = userService.addNewUser(userDto);
    	
    	UserDto returnUser = new UserDto();
    	
    	return returnUser;
    }

}
