package com.example.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.UsersEntity;


@Repository
public interface UserDao extends CrudRepository<UsersEntity,Integer>{

	@Query(value="select * from user where username=?1",nativeQuery=true)
	UsersEntity UserCheck(String username);
	
}