package io.github.dathin.dathinsession.controller;

import io.github.dathin.dathinsession.model.user.UserRequest;
import io.github.dathin.dathinsession.model.user.LoginResponse;
import io.github.dathin.dathinsession.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<Void> createUser(@Valid @RequestBody UserRequest userRequest) {
		userService.createUser(userRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("login")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.ok(userService.login(userRequest));
	}

	@PostMapping("logoff")
	public ResponseEntity<Void> logoff() {
		userService.logoff();
		return ResponseEntity.ok().build();
	}

	@PostMapping("validate")
	public ResponseEntity<Void> validate(
			@RequestHeader(name = HttpHeaders.AUTHORIZATION, required = false) String token) {
		userService.validate(token);
		return ResponseEntity.ok().build();
	}

}
