package com.ukma.library.controller;

import com.ukma.library.model.Book;
import com.ukma.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

	@Resource
	private BookService bookService;

	@GetMapping
	public List<Book> getAll() {
		return bookService.getAll();
	}

	@GetMapping("/{isbn}")
	public Book getBook(@PathVariable String isbn) {
		return bookService.getById(isbn);
	}

	@PostMapping
	public Book saveBook(@RequestBody Book book) {
		return bookService.save(book);
	}
}
