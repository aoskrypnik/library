package com.ukma.library.service;

import com.ukma.library.dto.BookFilterDto;
import com.ukma.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

	Book save(Book book, MultipartFile image);

	List<Book> getAll();

	Page<Book> search(BookFilterDto filter, Pageable pageable);

	Book getById(String isbn);

	Book updateBook(Book book, MultipartFile image, String isbn);

	void deleteBook(String isbn);
}
