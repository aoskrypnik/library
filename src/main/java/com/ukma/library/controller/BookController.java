package com.ukma.library.controller;

import com.ukma.library.dto.FilterDto;
import com.ukma.library.model.Book;
import com.ukma.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<Book> getAll(FilterDto filter) {
        return bookService.search(filter, null);
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

    @PutMapping(value = "/{isbn}", consumes = MULTIPART_FORM_DATA_VALUE)
    public Book updateBook(
            @RequestParam("book") Book book,
            @RequestParam(value = "image", required = false) MultipartFile image, @PathVariable String isbn) {
        return bookService.updateBook(book, image, isbn);
    }

    @DeleteMapping(value = "/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
		return ResponseEntity.noContent().build();
    }


}
