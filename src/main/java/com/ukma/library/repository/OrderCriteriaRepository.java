package com.ukma.library.repository;

import com.ukma.library.dto.OrderFilterDto;
import com.ukma.library.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCriteriaRepository {

	Page<Order> search(OrderFilterDto orderFilter, Pageable pageable);
}
