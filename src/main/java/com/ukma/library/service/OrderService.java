package com.ukma.library.service;

import com.ukma.library.dto.OrderFilterDto;
import com.ukma.library.dto.OrderSaveDto;
import com.ukma.library.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

	Order save(OrderSaveDto orderSaveDto);

	List<Order> getAll();

	Order getById(Long orderId);

	Order update(Order order, Long orderId);

	Page<Order> search(OrderFilterDto orderFilter, Pageable pageable);
}
