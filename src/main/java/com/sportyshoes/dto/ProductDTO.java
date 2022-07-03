package com.sportyshoes.dto;

import lombok.Data;

@Data
public class ProductDTO {
	private Long id;
	private String productName;
	private int categoryId;
	private int size;
	private double productPrice;
	private int stock;
	private String description;
	
}
