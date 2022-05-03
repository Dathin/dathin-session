package io.github.dathin.dathinsession.model.exception;

import io.github.dathin.boot.dathinstartermodel.exception.GenericException;
import org.springframework.http.HttpStatus;

public class UserDoesNotExistException extends GenericException {

	public UserDoesNotExistException() {
		super("Email and password does not exist. Please check your credentials", HttpStatus.BAD_REQUEST.value());
	}

}
