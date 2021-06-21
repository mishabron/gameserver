package com.mbronshteyn.authentication.services.impl;

import com.mbronshteyn.authentication.dto.UserDto;
import com.mbronshteyn.authentication.services.UserService;
import com.mbronshteyn.data.authentication.User;
import com.mbronshteyn.data.authentication.repository.UserRepository;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired		
	UserRepository userRepository;

	@Value("${password}")
	private String masterPassword;
	
	@Override
	public User addNewUser(UserDto userDto) {

		BasicTextEncryptor passwordEncryptor = new BasicTextEncryptor();
		passwordEncryptor.setPasswordCharArray(masterPassword.toCharArray());
		String secretPassword = passwordEncryptor.encrypt(userDto.getPassword());

		User user = new User();
		user.setEmail(userDto.getEmail());
		user.setUserId(userDto.getUserId());
		user.setPassword(secretPassword);
		user.setRole(userDto.getRole());
		user.setUpdateBy(userDto.getUpdateBy());
		
		user = userRepository.save(user);
		
		return user;
	}

}
