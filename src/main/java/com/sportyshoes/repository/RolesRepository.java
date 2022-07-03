package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sportyshoes.model.Role;


public interface RolesRepository extends JpaRepository<Role, Integer> {
	
}
