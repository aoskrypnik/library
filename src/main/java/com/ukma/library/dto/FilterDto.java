package com.ukma.library.dto;

import lombok.Data;

@Data
public class FilterDto {

	private String author;
	private String genre;
	private boolean isAvailableOnly;
}
