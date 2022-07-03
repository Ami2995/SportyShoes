package com.sportyshoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sportyshoes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	public List<Product> findAllByCategory_Id(int id);
	
	@Query("Select product from Product product where product.id=?1")
	public List<Product> findProductByID(Long id);
}
