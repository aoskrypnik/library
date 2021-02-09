package com.ukma.library.service.impl;

import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Book;
import com.ukma.library.model.BookState;
import com.ukma.library.model.Copy;
import com.ukma.library.repository.BookRepository;
import com.ukma.library.repository.CopyRepository;
import com.ukma.library.service.BookService;
import com.ukma.library.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	private static final String BOOK_NOT_FOUND_WITH_ISBN = "book not found with isbn ";
	private static final String FAILED_TO_UPLOAD_AN_IMAGE_FOR_BOOK = "Failed to upload an image for book {}";

	@Resource
	private FileService fileService;
	@Resource
	private BookRepository bookRepository;
	@Resource
	private CopyRepository copyRepository;

	@Override
	@Transactional
	public Book save(Book book, MultipartFile file) {
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

		try {
			book.setImageLink(fileService.uploadFile(file));
		} catch (IOException e) {
			log.error(FAILED_TO_UPLOAD_AN_IMAGE_FOR_BOOK, book.getIsbn());
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
