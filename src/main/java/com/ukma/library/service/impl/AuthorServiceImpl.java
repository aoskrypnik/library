package com.ukma.library.service.impl;

import com.ukma.library.model.Author;
import com.ukma.library.repository.AuthorRepository;
import com.ukma.library.service.AuthorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Resource
	private AuthorRepository authorRepository;

	@Override
	public List<Author> getAll() {
		return authorRepository.findAll();
	}

	@PostConstruct
	void buildTestAuthors() {
		Author author1 = Author.builder().authorName("Taras Shevchenko").country("Ukraine").build();
		Author author2 = Author.builder().authorName("Franz Kafka").country("Czeck Republic").build();
		Author author3 = Author.builder().authorName("Jack London").country("USA").build();
		authorRepository.saveAll(List.of(author1, author2, author3));
	}
}
