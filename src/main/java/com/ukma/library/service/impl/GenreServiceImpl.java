package com.ukma.library.service.impl;

import com.ukma.library.model.Genre;
import com.ukma.library.repository.GenreRepository;
import com.ukma.library.service.GenreService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

	@Resource
	private GenreRepository genreRepository;

	@Override
	public List<Genre> getAll() {
		return genreRepository.findAll();
	}

	@PostConstruct
	void buildTestGenres() {
		List<Genre> genres = List.of(
				Genre.builder().genreName("thriller").build(),
				Genre.builder().genreName("romantic").build(),
				Genre.builder().genreName("detective").build(),
				Genre.builder().genreName("action and adventure").build(),
				Genre.builder().genreName("biography").build(),
				Genre.builder().genreName("classics").build(),
				Genre.builder().genreName("fantasy").build(),
				Genre.builder().genreName("fiction").build(),
				Genre.builder().genreName("historical fiction").build(),
				Genre.builder().genreName("horror").build(),
				Genre.builder().genreName("memoir").build(),
				Genre.builder().genreName("poetry").build(),
				Genre.builder().genreName("novel").build(),
				Genre.builder().genreName("nonfiction").build()
		);
		genres.forEach(genre -> {
			if (genreRepository.findByGenreName(genre.getGenreName()).isEmpty()) {
				genreRepository.save(genre);
			}
		});
	}
}
