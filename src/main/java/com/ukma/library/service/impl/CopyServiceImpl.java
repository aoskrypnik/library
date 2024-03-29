package com.ukma.library.service.impl;

import com.ukma.library.model.Copy;
import com.ukma.library.repository.CopyRepository;
import com.ukma.library.service.CopyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class CopyServiceImpl implements CopyService {

	@Resource
	private CopyRepository copyRepository;

	@Override
	public Copy save(Copy copy) {
		return copyRepository.save(copy);
	}

	@Override
	public Optional<Copy> getById(Long id) {
		return copyRepository.findById(id);
	}

	@Override
	public Optional<Copy> getFirstByBookIsbnAndIsAvailable(String isbn) {
		return copyRepository.findFirstByBookIsbnAndIsAvailableTrue(isbn);
	}
}
