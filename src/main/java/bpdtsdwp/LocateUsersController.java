package bpdtsdwp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class LocateUsersController {
    private static final double METRES_IN_A_MILE = 1609.34;

    private final UsersClient users;
    private final String targetCity;
    private final double targetLatitude;
    private final double targetLongitude;
    private final double withinMetres;

    public LocateUsersController(UsersClient users,
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

    @GetMapping("/users/inOrNearLondon")
    public List<UserDetails> usersInOrNearCity() {
        Stream<User> usersInCity = users.inTargetCity(targetCity);
        Stream<User> usersWithDistanceOfCity = users.withinDistanceOfTarget(targetLatitude, targetLongitude, withinMetres);

        return Stream.concat(usersInCity, usersWithDistanceOfCity)
                .map(user -> new UserDetails(user.id(), user.firstName(), user.lastName()))
                .distinct()
                .toList();
    }


}
