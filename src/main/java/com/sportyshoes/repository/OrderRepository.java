package com.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sportyshoes.model.Orders;

public interface OrderRepository  extends JpaRepository<Orders, Integer>{

	@Query("Select order from Orders order where order.user=?1")
	public List<Orders> getOrderByUserId(int id);
	
}
