package com.sportyshoes.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sportyshoes.global.GlobalData;
import com.sportyshoes.model.Role;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.RolesRepository;
import com.sportyshoes.repository.UserRepository;


@Controller
public class LoginController {
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RolesRepository roleRepo;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}	
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		String password = user.getPassword();
		user.setPassword(bcrypt.encode(password));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findById(2).get());
		user.setRoles(roles);	
		userRepo.save(user);
		request.login(user.getEmail(), password);
		return "redirect:/";
	}
}
