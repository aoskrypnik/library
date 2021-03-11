package com.ukma.library.repository;

import com.ukma.library.dto.FilterDto;
import com.ukma.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCriteriaRepository {

	Page<Book> search(FilterDto filter, Pageable pageable);
}
