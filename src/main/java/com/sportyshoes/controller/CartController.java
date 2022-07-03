package com.sportyshoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sportyshoes.global.GlobalData;
import com.sportyshoes.model.CustomUserDetails;
import com.sportyshoes.model.Product;
import com.sportyshoes.service.ProductService;


@Controller
public class CartController {

	@Autowired
	private ProductService productService;

	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable Long id) {
		GlobalData.cart.add(productService.getProductById(id).get());
		return "redirect:/shop";
	}
	
	@GetMapping("/cart")
	public String getCart(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product:: getProductPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String removeItem(@PathVariable int index) {
		GlobalData.cart.remove(index);
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(@AuthenticationPrincipal CustomUserDetails user, Model model) {
		model.addAttribute("user", user);
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product:: getProductPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		return "checkout";
	}
	
}
