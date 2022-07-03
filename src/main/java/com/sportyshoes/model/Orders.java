package com.sportyshoes.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId")
	private User user;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "purchaseOrder_Shoes",
            joinColumns = {@JoinColumn(name = "purchaseOrderId", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "shoeId", referencedColumnName = "PRODUCT_ID")})
	private List<Product> shoes = new ArrayList<Product>();
	private String contact;
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date purchaseDate;
	@PrePersist
	private void onCreate() {
		purchaseDate = new Date();
	}
	private double billedAmount;
	private String shippingAddress;
	private String city;
	private int postalCode;
	
	public Orders(Orders order) {
		this.user = order.getUser();
		this.shoes = order.getShoes();
		this.contact = order.getContact();
 		this.purchaseDate = order.getPurchaseDate();
		this.billedAmount = order.getBilledAmount();
		this.shippingAddress = order.getShippingAddress();
		this.postalCode = order.getPostalCode();
	}
	
	public Orders() {
		
	}
}
