package com.ukma.library.repository;

import com.ukma.library.dto.FilterDto;
import com.ukma.library.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCriteriaRepository {

	List<Book> search(FilterDto filter, Pageable pageable);
}
