package com.sportyshoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sportyshoes.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
