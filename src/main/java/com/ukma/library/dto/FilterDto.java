package com.ukma.library.dto;

import lombok.Data;

@Data
public class FilterDto {

	String author;
	String genre;
	boolean isAvailableOnly;
}
