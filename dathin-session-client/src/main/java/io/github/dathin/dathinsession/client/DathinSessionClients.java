package io.github.dathin.dathinsession.client;

import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserClientConfig.class)
public class DathinSessionClients {

	@Bean
	public UserClient userClient(InternalClientBuilder internalClientBuilder, UserClientConfig userClientConfig) {
		return internalClientBuilder.buildClient(UserClient.class, userClientConfig.getUrl());
	}

}
