package com.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sportyshoes.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{ 

	@Query("SELECT user FROM User user WHERE CONCAT(user.firstName, ' ', user.lastName, ' ', user.email, ' ') LIKE %?1%")
	public List<User> search(String keyword);
	
	@Query("Select user from User user where user.email = :email")
	public User getUserByEmail(@Param("email") String email );
	
	@Query("Select user from User user where user.email = ?1")
	public List<User> getUserByMail(String email);
}
