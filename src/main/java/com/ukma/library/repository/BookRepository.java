package com.ukma.library.repository;

import com.ukma.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	Book save(Book book);

	List<Book> findAll();

	Optional<Book> getBookByIsbn(String isbn);
}
