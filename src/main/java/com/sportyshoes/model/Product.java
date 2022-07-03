package com.sportyshoes.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="product_id")
	private Long id;
	@Column(name="product_name")
	private String productName;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="category_id", referencedColumnName = "category_id")
	private Category category;
	@Column()
	private int size;
	
	@Column(name="product_price")
	private double productPrice; 
	@Column(name="product_stock")
	private int stock;
	@Column(name="product_desc")
	private String description;
	
	@ManyToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Orders> purchaseOrder = new ArrayList<Orders>();

	@Override
	public String toString() {
		return this.productName;
	}

	
}
