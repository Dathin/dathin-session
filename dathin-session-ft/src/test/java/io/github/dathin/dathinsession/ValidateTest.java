package io.github.dathin.dathinsession;

import io.github.dathin.boot.dathinstartermodel.exception.GenericException;
import io.github.dathin.dathinsession.client.UserClient;
import io.github.dathin.dathinsession.model.user.UserRequest;
import io.github.dathin.dathinsession.utils.UserClientUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ValidateTest {

	static UserClient userClient;

	@BeforeAll
	static void beforeAll() {
		userClient = UserClientUtils.getUserClient();
	}

	@Test
	void invalidAuthorization() {
		Assertions.assertThrows(GenericException.class, userClient::validateUser);
	}

	@Test
	void validate() {
		var userRequest = new UserRequest();
		userRequest.setEmail(UUID.randomUUID() + "@dathinlabs.com");
		userRequest.setPassword(UUID.randomUUID().toString());
		userClient.createUser(userRequest);

		var login = userClient.login(userRequest);

		var userClient = UserClientUtils.getUserClient(login.getToken());
		Assertions.assertDoesNotThrow(userClient::validateUser);
	}

}
