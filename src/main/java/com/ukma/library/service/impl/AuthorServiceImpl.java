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
		List<Author> authors = List.of(
				Author.builder().authorName("Taras Shevchenko").country("Ukraine").build(),
				Author.builder().authorName("Franz Kafka").country("Czeck Republic").build(),
				Author.builder().authorName("Jack London").country("USA").build(),
				Author.builder().authorName("William Shakespeare").country("Great Britain").build(),
				Author.builder().authorName("Arthur Conan Doyle").country("Great Britain").build(),
				Author.builder().authorName("Edgar Allan Poe").country("Great Britain").build(),
				Author.builder().authorName("Oscar Wilde").country("Great Britain").build(),
				Author.builder().authorName("Charles Dickens").country("Great Britain").build(),
				Author.builder().authorName("Ernest Hemingway").country("USA").build(),
				Author.builder().authorName("Jane Austen").country("Great Britain").build(),
				Author.builder().authorName("Joanne Rowling").country("Great Britain").build(),
				Author.builder().authorName("Lewis Carroll").country("Great Britain").build(),
				Author.builder().authorName("Agatha Christie").country("Great Britain").build(),
				Author.builder().authorName("Mark Twain").country("USA").build(),
				Author.builder().authorName("Friedrich Nietzsche").country("Germany").build(),
				Author.builder().authorName("Stephen King").country("USA").build()
		);
		authors.forEach(author -> {
			if (authorRepository.findByAuthorName(author.getAuthorName()).isEmpty()) {
				authorRepository.save(author);
			}
		});
	}
}
