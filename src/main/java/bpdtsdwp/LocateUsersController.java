package bpdtsdwp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
public class LocateUsersController {
    private static final double METRES_IN_A_MILE = 1609.34;

    private final WebClient client;
    private final String targetCity;
    private final double targetLatitude;
    private final double targetLongitude;
    private final double withinMetres;

    public LocateUsersController(WebClient client,
                                 @Value("${city.name}") String targetCity,
                                 @Value("${city.latitude}") double targetLatitude,
                                 @Value("${city.longitude}") double targetLongitude,
                                 @Value("${city.withinMiles}") double withinMiles) {
        this.client = client;
        this.targetCity = targetCity;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.withinMetres = withinMiles * METRES_IN_A_MILE;
    }

    @GetMapping("/users/inOrNearLondon")
    public List<UserDetails> usersInOrNearCity() {
        Stream<User> usersInLondon = usersInTargetCity();
        Stream<User> usersWithDistanceOfLondon = usersWithinDistanceOfTargetCity();

        return Stream.concat(usersInLondon, usersWithDistanceOfLondon)
                .map(user -> new UserDetails(user.id(), user.firstName(), user.lastName()))
                .distinct()
                .toList();
    }

    private Stream<User> usersInTargetCity() {
        ResponseEntity<List<User>> response = client.get()
                .uri("/city/{city}/users", targetCity)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {})
                .share()
                .block();

        List<User> usersInTargetCity = Objects.requireNonNull(response).getBody();
        return Objects.requireNonNull(usersInTargetCity).stream();
    }

    private Stream<User> usersWithinDistanceOfTargetCity() {
        ResponseEntity<List<User>> response = client.get()
                .uri("/users")
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<User>>() {})
                .share()
                .block();

        List<User> allUsers = Objects.requireNonNull(response).getBody();

        return Objects.requireNonNull(allUsers).stream()
                .filter(this::withinDistanceOfTargetCity);
    }

    private boolean withinDistanceOfTargetCity(User user) {
        return Haversine.distance(targetLatitude, targetLongitude, user.latitude(), user.longitude()) <= withinMetres;
    }

}
