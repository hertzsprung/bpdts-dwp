package bpdtsdwp;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    @Autowired
    private WebTestClient client;

    private WebTestClient.ResponseSpec response;

    @When("I locate users")
    public void locateUsers() {
        response = client.get()
                .uri("/users/inOrNearLondon")
                .exchange();
    }

    @Then("I receive exactly these users in any order:")
    public void assertLocateUsersResponse(List<UserDetails> expected) {
        UserDetails[] expectedUsers = expected.toArray(new UserDetails[0]);

        response.expectBody(listOfUserDetails())
                .consumeWith(result -> assertThat(result.getResponseBody()).containsExactlyInAnyOrder(expectedUsers));
    }

    private ParameterizedTypeReference<List<UserDetails>> listOfUserDetails() {
        return new ParameterizedTypeReference<>() {};
    }

    @DataTableType
    public UserDetails userDetails(Map<String, String> row) {
        return new UserDetails(Long.parseLong(row.get("id")), row.get("first_name"), row.get("last_name"));
    }
}
