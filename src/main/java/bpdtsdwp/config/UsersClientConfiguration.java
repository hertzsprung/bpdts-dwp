package bpdtsdwp.config;

import bpdtsdwp.client.UsersClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UsersClientConfiguration {
    @Value("${bpdtsTestApp.baseUrl}")
    private String baseUrl;

    @Bean
    public UsersClient usersClient() {
        return new UsersClient(restTemplate());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri(baseUrl).build();
    }
}
