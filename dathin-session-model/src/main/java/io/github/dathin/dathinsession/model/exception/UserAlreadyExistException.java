package io.github.dathin.dathinsession.model.exception;

import io.github.dathin.boot.dathinstartermodel.exception.GenericException;

public class UserAlreadyExistException extends GenericException {

	public UserAlreadyExistException() {
		super("Email already taken", 400);
	}

}
