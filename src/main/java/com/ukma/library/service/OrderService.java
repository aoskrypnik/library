package com.ukma.library.service;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

	Order save(OrderSaveDto orderSaveDto);

	List<Order> getAll();

	Order getById(Long orderId);

	Order updateOrder(Order order, Long orderId);
}
