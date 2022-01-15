package bpdtsdwp;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {

    @TestConfiguration
    static class Configuration {
        @Bean
        public WebTestClient webTestClient(ApplicationContext applicationContext) {
            return WebTestClient.bindToApplicationContext(applicationContext)
                    .build()
                    .mutate()
                    .responseTimeout(Duration.ofSeconds(10))
                    .build();
        }
    }
}
