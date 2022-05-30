package io.github.dathin.dathinsession;

import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilder;
import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilderConfig;
import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilderFactory;
import io.github.dathin.dathinsession.client.DathinSessionClients;
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
public class CreateUserTest {

	static UserClient userClient;

	@BeforeAll
	static void beforeAll() {
		userClient = UserClientUtils.getUserClient();
	}

	@Test
	void createUser() {
		var userRequest = new UserRequest();
		userRequest.setEmail(UUID.randomUUID() + "@dathinlabs.com");
		userRequest.setPassword(UUID.randomUUID().toString());

		Assertions.assertDoesNotThrow(() -> userClient.createUser(userRequest));
	}

}
