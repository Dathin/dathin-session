package io.github.dathin.dathinsession.client;

import io.github.dathin.boot.dathinstarterclient.client.BaseClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client.user")
public class UserClientConfig extends BaseClientConfig {

}
