package bpdtsdwp.config;

import bpdtsdwp.service.UserLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserLocatorConfiguration {
    @Autowired
    private UsersClientConfiguration usersClientConfiguration;

    @Bean
    public UserLocator userLocator(@Value("${city.name}") String targetCity,
                                   @Value("${city.latitude}") double targetLatitude,
                                   @Value("${city.longitude}") double targetLongitude,
                                   @Value("${city.withinMiles}") double withinMiles) {
        return new UserLocator(usersClientConfiguration.usersClient(), targetCity, targetLatitude, targetLongitude, withinMiles);
    }
}
