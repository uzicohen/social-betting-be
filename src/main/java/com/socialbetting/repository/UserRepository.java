package com.socialbetting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.socialbetting.objectmodel.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
    @Query("SELECT u FROM _user u where u.username = :username")
	User findByUsername(@Param("username") String username);
}
