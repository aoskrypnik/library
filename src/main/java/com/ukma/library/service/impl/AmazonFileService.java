package com.ukma.library.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ukma.library.exception.BrokenFileException;
import com.ukma.library.exception.UploadRejectionException;
import com.ukma.library.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Service
public class AmazonFileService implements FileService {

	private static final Character SLASH = '/';
	private static final Character HYPHEN = '-';
	private static final Character SPACE = ' ';
	private static final Character UNDERSCORE = '_';
	private static final String IMAGE = "image";
	private static final String TRIED_TO_UPLOAD_NOT_IMAGE_MESSAGE = "Tried to upload not an image";
	public static final String CAN_NOT_GET_FILE_ORIGINAL_FILENAME_MESSAGE =
			"Can not get file's original filename";

	private AmazonS3 amazonS3;

	@Value("${amazonProperties.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonProperties.bucketName}")
	private String bucketName;
	@Value("${amazonProperties.accessKey}")
	private String accessKey;
	@Value("${amazonProperties.secretKey}")
	private String secretKey;
	@Value("${amazonProperties.region}")
	private String region;
	@Value("${amazonProperties.imageUrl}")
	private String imageUrl;

	@Override
	public String uploadFile(MultipartFile image) throws IOException {
		if (isEmpty(image.getContentType()) || !image.getContentType().contains(IMAGE)) {
			throw new UploadRejectionException(TRIED_TO_UPLOAD_NOT_IMAGE_MESSAGE);
		}
		return uploadFileToS3Bucket(image);
	}

	private String uploadFileToS3Bucket(MultipartFile multipartFile)
			throws IOException, BrokenFileException {
		File file = convertMultiPartToFile(multipartFile);
		String fileName = generateFileName(multipartFile.getOriginalFilename());
		try {
			amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file));
		} finally {
			file.delete();
		}
		return imageUrl + SLASH + fileName;
	}

	private File convertMultiPartToFile(MultipartFile multipartFile)
			throws IOException, BrokenFileException {
		if (isNull(multipartFile.getOriginalFilename())) {
			throw new BrokenFileException(
					CAN_NOT_GET_FILE_ORIGINAL_FILENAME_MESSAGE + multipartFile.getName());
		}
		File convertedFile = new File(multipartFile.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(multipartFile.getBytes());
		fos.close();
		return convertedFile;
	}

	private String generateFileName(String fileName) {
		return LocalDateTime.now().toString() + HYPHEN + fileName.replace(SPACE, UNDERSCORE);
	}

	@PostConstruct
	private void initializeAmazon() {
		this.amazonS3 = AmazonS3ClientBuilder
				.standard()
				.withRegion(region)
				.withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey))
				)
				.build();
	}
}
