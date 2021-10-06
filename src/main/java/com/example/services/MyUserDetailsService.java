package com.example.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entity.UsersEntity;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	UserDao userDao;
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{	
			UsersEntity Users = userDao.UserCheck(userName);
			if(Users.getUsername().equals(userName)) {
				return new User(userName,Users.getPassword(),new ArrayList<>());
			}else {
				throw new UsernameNotFoundException("User not found with username: " + userName); 
			}
	}
	
}
