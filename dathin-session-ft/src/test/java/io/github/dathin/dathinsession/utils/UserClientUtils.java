package io.github.dathin.dathinsession.utils;

import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilder;
import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilderConfig;
import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilderFactory;
import io.github.dathin.dathinsession.client.DathinSessionClients;
import io.github.dathin.dathinsession.client.UserClient;
import io.github.dathin.dathinsession.client.UserClientConfig;

public class UserClientUtils {

	public static UserClient getUserClient() {
		return getUserClient(null);
	}

	public static UserClient getUserClient(String authorization) {
		var internalClientBuilderFactory = new InternalClientBuilderFactory();
		var dathinSessionClients = new DathinSessionClients();
		var internalClientBuilderConfig = new InternalClientBuilderConfig();
		internalClientBuilderConfig.setHeaderPropagation(false);
		InternalClientBuilder internalClientBuilder = internalClientBuilderFactory
				.getNewInstance(internalClientBuilderConfig);
		internalClientBuilder.setAuthorization(authorization);
		var userClientConfig = new UserClientConfig();
		userClientConfig.setUrl("http://localhost:9000");
		return dathinSessionClients.userClient(internalClientBuilder, userClientConfig);
	}

}
