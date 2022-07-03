package com.sportyshoes.model;

import java.util.ArrayList;
import java.util.Iterator;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotEmpty
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotEmpty
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@NotEmpty
	@Column(nullable = false, unique = true)
	@Email(message = "message= {errors.invalid.email}")
	private String email;
	@NotEmpty
	private String password;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "ID") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID") })
	private List<Role> roles = new ArrayList<Role>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private List<Orders> orders = new ArrayList<Orders>();

	public User(User user) {
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.roles = user.getRoles();
		this.orders = user.getOrders();
	}

	public User() {

	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public boolean hasRole(String roleName) {
		Iterator<Role> iterator = roles.iterator();
		Role role = iterator.next();
		if (role.getName().equals(roleName)) {
			return true;
		}
		return false;
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
}
