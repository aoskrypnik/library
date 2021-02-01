package com.ukma.library.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@GetMapping("/onlyAdministrator")
	public ResponseEntity<String> onlyAdministrator() {
		return ResponseEntity.ok("onlyAdministrator");
	}

	@GetMapping("/onlyReader")
	public ResponseEntity<String> onlyReader() {
		return ResponseEntity.ok("onlyReader");
	}

	@GetMapping("/all")
	public ResponseEntity<String> all() {
		return ResponseEntity.ok("all");
	}
}
