package com.ukma.library.exception;

public class NoCopiesAvailableException extends RuntimeException {

	public NoCopiesAvailableException(String isbn) {
		super("No copies available of book " + isbn);
	}
}
