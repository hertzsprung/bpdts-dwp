package bpdtsdwp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class LocateUsersApplication {
    @Value("${bpdtsTestApp.baseUrl}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.create(baseUrl);
    }

    public static void main(String... args) {
        SpringApplication.run(LocateUsersApplication.class, args);
    }
}
