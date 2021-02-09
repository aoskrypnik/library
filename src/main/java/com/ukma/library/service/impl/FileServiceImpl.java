package com.ukma.library.service.impl;

import com.ukma.library.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	private static final char SLASH = '/';
	private static final char DOT = '.';

	@Value("${upload.path}")
	private String uploadPath;

	@Override
	public String uploadFile(MultipartFile image) throws IOException {
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		String fileUuid = UUID.randomUUID().toString();
		String fileName = fileUuid + DOT + image.getOriginalFilename();
		image.transferTo(new File(uploadPath + SLASH + fileName));

		return fileName;
	}
}
