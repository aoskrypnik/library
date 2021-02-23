package com.ukma.library.repository;

import com.ukma.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	Optional<Book> getBookByIsbn(String isbn);

	void deleteBookByIsbn(String isbn);
}
