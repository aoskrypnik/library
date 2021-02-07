package com.ukma.library.controller;

import com.ukma.library.model.Genre;
import com.ukma.library.service.GenreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

	@Resource
	private GenreService genreService;

	@GetMapping
	public List<Genre> getAll() {
		return genreService.getAll();
	}
}
