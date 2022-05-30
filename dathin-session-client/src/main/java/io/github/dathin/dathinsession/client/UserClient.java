package io.github.dathin.dathinsession.client;

import feign.HeaderMap;
import feign.Headers;
import feign.RequestLine;
import io.github.dathin.dathinsession.model.user.LoginResponse;
import io.github.dathin.dathinsession.model.user.UserRequest;

import java.util.Collections;
import java.util.Map;

public interface UserClient {

	@RequestLine("POST /user")
	void createUser(UserRequest userRequest);

	@RequestLine("POST /user/validate")
	void validateUser();

	@RequestLine("POST /user/logoff")
	void logoff();

	@RequestLine("POST /user/login")
	LoginResponse login(UserRequest userRequest);

}
