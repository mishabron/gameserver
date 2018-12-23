package com.mbronshteyn.authentication.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbronshteyn.data.authentication.User;
import com.mbronshteyn.data.authentication.repository.UserRepository;
import com.mbronshteyn.authentication.dto.UserDto;
import com.mbronshteyn.authentication.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired		
	UserRepository userRepository;
	
	@Override
	public User addNewUser(UserDto userDto) {
		
		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setUserId(userDto.getUserId());
		user.setPassword(userDto.getPassword());
		user.setRole(userDto.getRole());
		user.setUpdateBy(userDto.getUpdateBy());
		
		user = userRepository.save(user);
		
		return user;
	}

}
