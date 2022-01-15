package bpdtsdwp.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.springframework.http.HttpMethod.GET;

/**
 * Web client for communicating with the BPDTS test API.
 */
public class UsersClient {
    private final RestTemplate client;

    public UsersClient(RestTemplate client) {
        this.client = client;
    }

    public Stream<User> inTargetCity(String targetCity) {
        List<User> usersInTargetCity = client.exchange("/city/{city}/users", GET, null, listOfUsers(), targetCity)
                .getBody();

        return Objects.requireNonNull(usersInTargetCity).stream();
    }

    public Stream<User> withinDistanceOfTarget(double targetLatitude, double targetLongitude, double withinMetres) {
        Predicate<User> withinDistanceOfTargetCity = user -> {
            double distance = Haversine.distanceInMetres(targetLatitude, targetLongitude, user.latitude(), user.longitude());
            return distance <= withinMetres;
        };

        return all().filter(withinDistanceOfTargetCity);
    }

    private Stream<User> all() {
        List<User> allUsers = client.exchange("/users", GET, null, listOfUsers())
                .getBody();

        return Objects.requireNonNull(allUsers).stream();
    }

    private ParameterizedTypeReference<List<User>> listOfUsers() {
        return new ParameterizedTypeReference<>() {};
    }
}
