package com.ukma.library.service;

import com.ukma.library.model.Book;

import java.util.List;

public interface BookService {

	Book save(Book book);

	List<Book> getAll();

	Book getById(String isbn);
}
