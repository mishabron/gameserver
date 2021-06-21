package com.mbronshteyn.data.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.data.authentication.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	
	User findByUserIdAndPassword(String userID, String password);

	User findByUserId(String userID);
	
}
