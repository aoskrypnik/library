package com.ukma.library.service;

import com.ukma.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

	Book saveBook(Book book);

	List<Book> getAllBooks();

	Optional<Book> getBookById(String isbn);
}
