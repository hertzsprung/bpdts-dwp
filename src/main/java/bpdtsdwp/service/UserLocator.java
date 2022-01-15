package bpdtsdwp.service;

import bpdtsdwp.client.User;
import bpdtsdwp.client.UsersClient;
import org.springframework.beans.factory.annotation.Value;

import java.util.stream.Stream;

public class UserLocator {
    private static final double METRES_IN_A_MILE = 1609.34;

    private final UsersClient users;
    private final String targetCity;
    private final double targetLatitude;
    private final double targetLongitude;
    private final double withinMetres;

    public UserLocator(UsersClient users,
                       @Value("${city.name}") String targetCity,
                       @Value("${city.latitude}") double targetLatitude,
                       @Value("${city.longitude}") double targetLongitude,
                       @Value("${city.withinMiles}") double withinMiles) {
        this.users = users;
        this.targetCity = targetCity;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.withinMetres = withinMiles * METRES_IN_A_MILE;
    }

    public Stream<UserDetails> usersInOrNearCity() {
        Stream<User> usersInCity = users.inTargetCity(targetCity);
        Stream<User> usersWithDistanceOfCity = users.withinDistanceOfTarget(targetLatitude, targetLongitude, withinMetres);

        return Stream.concat(usersInCity, usersWithDistanceOfCity)
                .map(user -> new UserDetails(user.id(), user.firstName(), user.lastName()))
                .distinct();
    }
}
