package io.github.dathin.dathinsession.model.exception;

import io.github.dathin.boot.dathinstartermodel.exception.GenericException;

public class UserDoesNotExistException extends GenericException {

	public UserDoesNotExistException() {
		super("Email and password does not exist. Please check your credentials", 400);
	}

}
