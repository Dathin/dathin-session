package io.github.dathin.dathinsession.service;

import io.github.dathin.boot.dathinstarterauthorizer.model.exception.UnauthorizedException;
import io.github.dathin.boot.dathinstarterauthorizer.service.AuthenticationService;
import io.github.dathin.dathinsession.dao.UserCache;
import io.github.dathin.dathinsession.model.exception.UserAlreadyExistException;
import io.github.dathin.dathinsession.model.exception.UserDoesNotExistException;
import io.github.dathin.dathinsession.mapper.UserMapper;
import io.github.dathin.dathinsession.model.user.LoginResponse;
import io.github.dathin.dathinsession.model.user.User;
import io.github.dathin.dathinsession.model.user.UserRequest;
import io.github.dathin.dathinsession.dao.UserDao;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

	private final UserDao userDao;

	private final UserCache userCache;

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final UserMapper userMapper;

	private final AuthenticationService authenticationService;

	public UserService(UserDao userDao, UserCache userCache, PasswordEncoder passwordEncoder, JwtService jwtService,
			UserMapper userMapper, AuthenticationService authenticationService) {
		this.userDao = userDao;
		this.userCache = userCache;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.userMapper = userMapper;
		this.authenticationService = authenticationService;
	}

	public void createUser(UserRequest userRequest) {
		encryptPassword(userRequest);
		try {
			userDao.insert(userRequest);
		}
		catch (DuplicateKeyException ex) {
			throw new UserAlreadyExistException();
		}
	}

	private void encryptPassword(UserRequest userRequest) {
		userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
	}

	public LoginResponse login(UserRequest loginRequest) {
		var optionalUser = userDao.getPasswordToLogin(loginRequest);
		var user = optionalUser.orElseThrow(UserDoesNotExistException::new);
		return checkPassword(user, loginRequest);
	}

	public void logoff() {
		userCache.deleteUserToken(authenticationService.getAuthenticatedUserOrThrow().getId());
	}

	public void validate(String token) {
		var userId = authenticationService.getAuthenticatedUserOrThrow().getId();
		var cachedToken = userCache.getUserToken(userId);
		if (!Objects.equals(cachedToken, jwtService.removeBearer(token))) {
			throw new UnauthorizedException();
		}
		userCache.resetExpire(userId);
	}

	private LoginResponse checkPassword(User user, UserRequest userRequest) {
		if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
			var token = jwtService.issueToken(user);
			userCache.cacheUserToken(user.getId(), token);
			return userMapper.tokenToLoginResponse(token);
		}
		throw new UserDoesNotExistException();
	}

}
