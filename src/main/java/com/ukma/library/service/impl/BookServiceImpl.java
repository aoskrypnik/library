package com.ukma.library.service.impl;

import com.ukma.library.dto.BookFilterDto;
import com.ukma.library.exception.IsbnNotMatchException;
import com.ukma.library.exception.ResourceNotFoundException;
import com.ukma.library.model.Book;
import com.ukma.library.model.BookState;
import com.ukma.library.model.Copy;
import com.ukma.library.repository.BookRepository;
import com.ukma.library.service.BookService;
import com.ukma.library.service.CopyService;
import com.ukma.library.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

	private static final String BOOK_NOT_FOUND_WITH_ISBN = "book not found with isbn ";
	private static final String FAILED_TO_UPLOAD_AN_IMAGE_FOR_BOOK = "Failed to upload an image for book {}";

	@Resource
	private FileService amazonFileService;
	@Resource
	private BookRepository bookRepository;
	@Resource
	private CopyService copyService;

	@Override
	@Transactional
	public Book save(Book book, MultipartFile file) {
		Set<Copy> copies = new HashSet<>();

		for (int i = 0; i < book.getCopiesNum(); i++) {
			copies.add(copyService.save(Copy.builder()
					.book(book)
					.isAvailable(true)
					.state(BookState.NEW)
					.orders(Collections.emptySet())
					.estimatedReturnDate(null)
					.build()));
		}

		try {
			book.setImageLink(amazonFileService.uploadFile(file));
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
	public Page<Book> search(BookFilterDto filter, Pageable pageable) {
		return bookRepository.search(filter, pageable);
	}

	@Override
	public Book getById(String isbn) {
		return bookRepository.getBookByIsbn(isbn)
				.orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND_WITH_ISBN + isbn));
	}

	@Override
	@Transactional
	public Book updateBook(Book book, MultipartFile image, String isbn) {
		if (!book.getIsbn().equals(isbn))
			throw new IsbnNotMatchException("Isbn in path and inside book object are different");
		Optional<Book> bookFromDbOp = bookRepository.getBookByIsbn(isbn);
		if (bookFromDbOp.isEmpty())
			throw new ResourceNotFoundException(BOOK_NOT_FOUND_WITH_ISBN + isbn);
		Book bookFromDb = bookFromDbOp.get();
		bookFromDb.setTitle(book.getTitle());
		bookFromDb.setAuthors(book.getAuthors());
		bookFromDb.setGenres(book.getGenres());
		bookFromDb.setLanguage(book.getLanguage());
		bookFromDb.setPublishYear(book.getPublishYear());
		bookFromDb.setPagesNum(book.getPagesNum());
		bookFromDb.setPublishCountry(book.getPublishCountry());
		if (image != null) {
			try {
				bookFromDb.setImageLink(amazonFileService.uploadFile(image));
			} catch (IOException e) {
				log.error(FAILED_TO_UPLOAD_AN_IMAGE_FOR_BOOK, isbn);
			}
		}
		return bookFromDb;
	}

	@Override
	@Transactional
	public void deleteBook(String isbn) {
		Book book = bookRepository.getBookByIsbn(isbn)
				.orElseThrow(() -> new ResourceNotFoundException(BOOK_NOT_FOUND_WITH_ISBN + isbn));
		bookRepository.deleteBookByIsbn(isbn);
		amazonFileService.deleteFile(book.getImageLink());
	}
}
