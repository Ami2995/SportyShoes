package com.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.model.Product;
import com.sportyshoes.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public Product addProduct(Product product) {
		return repo.save(product);
	}
	
	public List<Product> getAllProducts(){
		return repo.findAll();
	}
	
	public void deleteProductById(Long id) {
		repo.deleteById(id);
	}
	
	public Optional<Product> getProductById(Long id) {
		return repo.findById(id);
	}
	
	public List<Product> getByCategoryId(int id){
		return repo.findAllByCategory_Id(id);
	}

}
