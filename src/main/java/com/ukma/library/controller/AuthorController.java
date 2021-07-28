package com.ukma.library.controller;

import com.ukma.library.model.Author;
import com.ukma.library.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

	@Resource
	private AuthorService authorService;

	@GetMapping
	public List<Author> getAll() {
		return null;
	}
}
