package io.github.dathin.dathinsession.client;

import io.github.dathin.boot.dathinstarterclient.client.InternalClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClients {

    @Bean
    public UserClient userClient(InternalClientBuilder internalClientBuilder){
        return internalClientBuilder.buildClient(UserClient.class, "https://webhook.site/#!/5f5cc5a8-bc0f-49b5-b262-80e1aa74c5fb");
//        https://webhook.site/#!/5f5cc5a8-bc0f-49b5-b262-80e1aa74c5fb
    }

}
