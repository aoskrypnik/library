package com.ukma.library.service.impl;

import com.ukma.library.model.Book;
import com.ukma.library.model.BookState;
import com.ukma.library.model.Copy;
import com.ukma.library.repository.BookRepository;
import com.ukma.library.repository.CopyRepository;
import com.ukma.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private CopyRepository copyRepository;

	@Override
	public Book saveBook(Book bookToSave) {
		Set<Copy> copies = new HashSet<>();
		for (int i = 0; i < bookToSave.getCopiesNum(); i++) {
			copies.add(copyRepository.save(Copy.builder()
					.book(bookToSave)
					.isAvailable(true)
					.state(BookState.NEW)
					.orders(Collections.emptySet())
					.estimatedReturnDate(null)
					.build()));
		}
		bookToSave.setCopies(copies);
		bookToSave = bookRepository.save(bookToSave);
		return bookToSave;
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Optional<Book> getBookById(String isbn) {
		return bookRepository.getBookByIsbn(isbn);
	}
}
