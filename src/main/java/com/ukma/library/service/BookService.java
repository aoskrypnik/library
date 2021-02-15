package com.ukma.library.service;

import com.ukma.library.model.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

	Book save(Book book, MultipartFile image);

	List<Book> getAll();

	Book getById(String isbn);

	Book updateBook(Book book, MultipartFile image, String isbn);

	void deleteBook(String isbn);
}
