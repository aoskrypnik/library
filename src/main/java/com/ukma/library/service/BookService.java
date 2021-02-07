package com.ukma.library.service;

import com.ukma.library.model.Book;

import java.util.List;

public interface BookService {

	Book saveBook(Book book);

	List<Book> getAllBooks();

	Book getBookById(String isbn);
}
