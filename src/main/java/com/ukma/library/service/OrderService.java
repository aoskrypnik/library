package com.ukma.library.service;

import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;

import java.util.List;

public interface OrderService {

	Order save(OrderSaveDto orderSaveDto);

	List<Order> getAll();

	Order getById(Long orderId);

	Order update(Order order, Long orderId);

	List<Order> search(Long userId, OrderStatus status);
}
