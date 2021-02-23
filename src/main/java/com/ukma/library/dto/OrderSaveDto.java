package com.ukma.library.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderSaveDto {
	List<Long> copiesIds;
}
