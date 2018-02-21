package com.mbronshteyn.authentication.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbronshteyn.authentication.data.User;

public interface UserRepository extends JpaRepository<User, String>{
	
	
	User findByUserIdAndPassword(String userID, String password);
	
}
