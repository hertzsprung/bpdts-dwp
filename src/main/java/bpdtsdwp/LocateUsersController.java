package bpdtsdwp;

import bpdtsdwp.service.UserDetails;
import bpdtsdwp.service.UserLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocateUsersController {
    private final UserLocator locator;

    public LocateUsersController(UserLocator locator) {
        this.locator = locator;
    }

    @GetMapping("/users/inOrNearLondon")
    public List<UserDetails> usersInOrNearCity() {
        return locator.usersInOrNearCity().toList();
    }
}
