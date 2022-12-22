package com.misha.exception;

public class FileStorageException extends RuntimeException {
    /**
	 * Auther: Narender Singh
	 */
	

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}