package com.ukma.library.service;

import com.ukma.library.model.Copy;

import java.util.List;
import java.util.Optional;

public interface CopyService {
	Copy save(Copy copy);

	List<Copy> getAll();

	Optional<Copy> getById(Long id);
}
