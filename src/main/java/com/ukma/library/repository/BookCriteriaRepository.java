package com.ukma.library.repository;

import com.ukma.library.dto.BookFilterDto;
import com.ukma.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCriteriaRepository {

	Page<Book> search(BookFilterDto filter, Pageable pageable);
}
