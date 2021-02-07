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
		Genre genre1 = Genre.builder().genreName("thriller").build();
		Genre genre2 = Genre.builder().genreName("romantic").build();
		Genre genre3 = Genre.builder().genreName("detective").build();
		if (genreRepository.findByGenreName(genre1.getGenreName()).isEmpty()) {
			genreRepository.save(genre1);
		}
		if (genreRepository.findByGenreName(genre2.getGenreName()).isEmpty()) {
			genreRepository.save(genre2);
		}
		if (genreRepository.findByGenreName(genre3.getGenreName()).isEmpty()) {
			genreRepository.save(genre3);
		}
	}
}
