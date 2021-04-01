package com.ukma.library.dto;

import lombok.Data;

@Data
public class BookFilterDto {

	private String title;
	private String author;
	private String genre;
	private boolean isAvailableOnly;
}
