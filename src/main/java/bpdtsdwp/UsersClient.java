package bpdtsdwp;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

class UsersClient {
    private final WebClient client;

    public UsersClient(WebClient client) {
        this.client = client;
    }

    Stream<User> inTargetCity(String targetCity) {
        ResponseEntity<List<User>> response = client.get()
                .uri("/city/{city}/users", targetCity)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {})
                .share()
                .block();

        List<User> usersInTargetCity = Objects.requireNonNull(response).getBody();
        return Objects.requireNonNull(usersInTargetCity).stream();
    }

    Stream<User> withinDistanceOfTarget(double targetLatitude, double targetLongitude, double withinMetres) {
        ResponseEntity<List<User>> response = client.get()
                .uri("/users")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {})
                .share()
                .block();

        List<User> allUsers = Objects.requireNonNull(response).getBody();

        Predicate<User> withinDistanceOfTargetCity = user -> {
            double distance = Haversine.distanceInMetres(targetLatitude, targetLongitude, user.latitude(), user.longitude());
            return distance <= withinMetres;
        };

        return Objects.requireNonNull(allUsers).stream()
                .filter(withinDistanceOfTargetCity);
    }
}
