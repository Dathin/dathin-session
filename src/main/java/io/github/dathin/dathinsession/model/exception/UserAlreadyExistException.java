package io.github.dathin.dathinsession.model.exception;

import io.github.dathin.boot.dathinstartermodel.exception.GenericException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends GenericException {

	public UserAlreadyExistException() {
		super("Email already taken", HttpStatus.BAD_REQUEST.value());
	}

}
