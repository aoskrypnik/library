package com.ukma.library.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ukma.library.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class StringToBookConverter implements Converter<String, Book> {

	private static final String FAILED_TO_CONVERT_A_BOOK_FROM_STRING = "Failed to convert a book from string";

	@Resource
	private ObjectMapper objectMapper;

	@Override
	public Book convert(String source) {
		try {
			return objectMapper.readValue(source, Book.class);
		} catch (JsonProcessingException e) {
			log.error(FAILED_TO_CONVERT_A_BOOK_FROM_STRING);
			return null;
		}
	}
}
