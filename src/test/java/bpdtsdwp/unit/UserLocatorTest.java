package bpdtsdwp.unit;

import bpdtsdwp.client.User;
import bpdtsdwp.service.UserDetails;
import bpdtsdwp.service.UserLocator;
import bpdtsdwp.client.UsersClient;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserLocatorTest {
    private final UsersClient users = mock(UsersClient.class);

    @Test
    void deduplicateUsersLivingInAndCurrentlyNearLondon() {
        var user = new User(1, "London", "Dweller", "london.dweller@bpdtsdwp.com", "10.0.0.1", 51.5072, -0.1276);

        when(users.inTargetCity(any())).thenReturn(Stream.of(user));
        when(users.withinDistanceOfTarget(anyDouble(), anyDouble(), anyDouble())).thenReturn(Stream.of(user));

        var locator = new UserLocator(users, "London", 51.5072, -0.1276, 10);

        List<UserDetails> usersInOrNearLondon = locator.usersInOrNearCity().toList();
        assertThat(usersInOrNearLondon).containsExactlyInAnyOrder(new UserDetails(1, "London", "Dweller"));
    }
}
