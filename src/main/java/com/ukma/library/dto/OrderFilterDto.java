package com.ukma.library.dto;

import com.ukma.library.model.OrderStatus;
import lombok.Data;

@Data
public class OrderFilterDto {
	private Long userId;
	private OrderStatus status;
}
