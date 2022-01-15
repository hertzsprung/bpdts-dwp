package bpdtsdwp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@RestController
public class LocateUsersController {
    @Value("${bpdtsTestApp.baseUrl}")
    private String baseUrl;

    @GetMapping("/test")
    public List<UserDetails> test() {
        WebClient webClient = WebClient.create(baseUrl);

        ResponseEntity<List<User>> response = webClient.get()
                .uri("/users")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {})
                .share()
                .block();

        Objects.requireNonNull(response);
        Objects.requireNonNull(response.getBody());

        return response.getBody().stream()
                .map(user -> new UserDetails(user.firstName(), user.lastName()))
                .toList();
    }
}
