package com.sportyshoes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.model.Category;
import com.sportyshoes.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
	
	public Category addCategory(Category category) {
		return repo.save(category);
	}
	
	public List<Category> getAllCategory(){
		return repo.findAll();
	}
	
	public void deleteCategoryById(int id) {
		repo.deleteById(id);
	}
	
	public Optional<Category> getCategoryById(int id) {
		return repo.findById(id);
	}
}
