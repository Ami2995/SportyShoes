package com.sportyshoes.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sportyshoes.global.GlobalData;
import com.sportyshoes.model.Orders;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;
import com.sportyshoes.service.CustomUserDetailService;
import com.sportyshoes.service.OrderService;

@Controller
public class OrderController {
	
	@Autowired
	private CustomUserDetailService userService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Date - dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	
    @PostMapping("/order")
	public String saveOrder(Model model, Principal principal, @RequestParam("address") String address,
			                @RequestParam("city") String city, @RequestParam("code") int zip, @RequestParam("bill") double bill,
			                @RequestParam("id") List<Product> id, @RequestParam("contact") String contact) {
  
    	String username = principal.getName();
    	User user = userRepo.getUserByEmail(username);
   
    	
	Orders order = new Orders();
    order.setUser(userService.getUserById(user.getId()).get());
    order.setShoes(id);
    order.setContact(contact);
    order.setBilledAmount(bill);
    order.setShippingAddress(address);
    order.setCity(city);
    order.setPostalCode(zip);
    orderService.addOrder(order);
    GlobalData.cart.clear();
    return "orderPlaced";
	}
    
}
