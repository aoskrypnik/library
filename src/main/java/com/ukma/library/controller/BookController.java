package com.ukma.library.controller;

import com.ukma.library.model.Book;
import com.ukma.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<?> getBooks() {
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
	}

	@GetMapping("/{isbn}")
	public ResponseEntity<?> getBook(@PathVariable String isbn) {
		Optional<Book> book = bookService.getBookById(isbn);
		return book.isPresent()
				? ResponseEntity.ok(book.get())
				: ResponseEntity.notFound().build();
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveBook(@RequestBody Book book) {
		Book savedBook = bookService.saveBook(book);
		return ResponseEntity.ok(savedBook);
	}

}
