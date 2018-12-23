package com.mbronshteyn.authentication.services;

import com.mbronshteyn.data.authentication.User;
import com.mbronshteyn.authentication.dto.UserDto;

public interface UserService {
	
	User addNewUser(UserDto userDto);

}
