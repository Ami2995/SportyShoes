package com.sportyshoes.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.model.Orders;
import com.sportyshoes.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	public Orders addOrder(Orders order) {
		return orderRepo.save(order);
	}

	public List<Orders> getAllOrders() {
		return orderRepo.findAll();
	}

	public void deleteOrderById(int id) {
		orderRepo.deleteById(id);
	}

	public Optional<Orders> getOrderById(int id) {
		return orderRepo.findById(id);
	}

	public List<Orders> getOrdersByDate(String date) {
		List<Orders> orders = orderRepo.findAll();
		if (date == null) {
			return orders;
		}

		List<Orders> purchaseOrder = new ArrayList<Orders>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date searchDate = null;

		try {
			searchDate = sdf.parse(date);
		} catch (ParseException e) {
			return orders;
		}

		for (Orders order : orders) {
			if (order.getPurchaseDate().compareTo(searchDate) == 0) {
				purchaseOrder.add(order);
			}
		}
		return purchaseOrder;
	}
}
