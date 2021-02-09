package com.ukma.library.controller;

import com.ukma.library.model.Book;
import com.ukma.library.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

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

	@PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
	public Book saveBook(
			@RequestParam("book") Book book,
			@RequestParam("image") MultipartFile image
	) {
		return bookService.save(book, image);
	}
}
