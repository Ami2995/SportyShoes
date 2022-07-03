package com.sportyshoes.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sportyshoes.dto.ProductDTO;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.UserRepository;
import com.sportyshoes.service.CategoryService;
import com.sportyshoes.service.CustomUserDetailService;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ProductService;

@Controller
public class AdminController {

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CustomUserDetailService userService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/admin")
	public String adminHome() {
		return "adminHome";
	}

	@GetMapping("/admin/categories")
	public String getCategory(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		return "category";
	}

	@GetMapping("/admin/categories/add")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addCategory";
	}

	@PostMapping("/admin/categories/add")
	public String postCategory(@ModelAttribute("category") Category category) {
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id) {
		categoryService.deleteCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model) {
		Optional<Category> category = categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "addCategory";
		}
		return "404";
	}

	@GetMapping("/admin/products")
	public String getProducts(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "product";
	}

	@GetMapping("/admin/products/add")
	public String addProducts(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}

	@PostMapping("/admin/products/add")
	public String postProducts(@ModelAttribute("productDTO") ProductDTO productDTO) throws IOException {
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setProductName(productDTO.getProductName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setSize(productDTO.getSize());
		product.setProductPrice(productDTO.getProductPrice());
		product.setStock(productDTO.getStock());
		product.setDescription(productDTO.getDescription());
		productService.addProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProductById(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable Long id, Model model) {
		Product product = productService.getProductById(id).get();
		ProductDTO productDto = new ProductDTO();
		productDto.setId(product.getId());
		productDto.setProductName(product.getProductName());
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setSize(product.getSize());
		productDto.setStock(product.getStock());
		productDto.setDescription(product.getDescription());

		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDto);
		return "productsADD";
	}

	@GetMapping("/passwordChange")
	public String changePass() {
		return "passwordChange";
	}

	@PostMapping("/passwordChange")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, RedirectAttributes redirect) {
		String userName = principal.getName();
		User currentUser = userRepo.getUserByEmail(userName);
		if (bcrypt.matches(oldPassword, currentUser.getPassword())) {
			currentUser.setPassword(bcrypt.encode(newPassword));
			userRepo.save(currentUser);
			redirect.addFlashAttribute("success", "Password changed successfully");
		} else {
			redirect.addFlashAttribute("error", "Old password not match");
			return "redirect:/passwordChange";
		}
		return "redirect:/login";
	}

	@GetMapping("/admin/listUsers")
	public String user(Model model) {
		List<User> user = userRepo.findAll();
		model.addAttribute("users", user);
		return "listUsers";
	}

	@GetMapping("/admin/listUsers/search")
	public String searchUser(Model model, @Param("keyword") String keyword) {
		model.addAttribute("users", userService.searchUsers(keyword));
		model.addAttribute("keyword", keyword);
		return "listUsers";
	}

	@GetMapping("/admin/orders")
	public String manageOrders(Model model) {
		model.addAttribute("orders", orderService.getAllOrders());
		return "orders";
	}
}
