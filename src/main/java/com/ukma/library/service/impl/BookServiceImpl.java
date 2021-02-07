package com.ukma.library.service.impl;

import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Book;
import com.ukma.library.model.BookState;
import com.ukma.library.model.Copy;
import com.ukma.library.repository.BookRepository;
import com.ukma.library.repository.CopyRepository;
import com.ukma.library.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

	private static final String BOOK_NOT_FOUND_WITH_ISBN = "book not found with isbn ";

	@Resource
	private BookRepository bookRepository;
	@Resource
	private CopyRepository copyRepository;

	@Override
	@Transactional
	public Book save(Book book) {
		Set<Copy> copies = new HashSet<>();
		for (int i = 0; i < book.getCopiesNum(); i++) {
			copies.add(copyRepository.save(Copy.builder()
					.book(book)
					.isAvailable(true)
					.state(BookState.NEW)
					.orders(Collections.emptySet())
					.estimatedReturnDate(null)
					.build()));
		}
		book.setCopies(copies);
		return book;
	}

	@Override
	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	@Override
	public Book getById(String isbn) {
		return bookRepository.getBookByIsbn(isbn)
				.orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND_WITH_ISBN + isbn));
	}
}
