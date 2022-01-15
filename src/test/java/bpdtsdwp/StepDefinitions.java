package bpdtsdwp;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitions {
    @Autowired
    private ApplicationContext applicationContext;

    private WebTestClient.ResponseSpec response;

    @When("I locate users")
    public void locateUsers() {
        WebTestClient client = WebTestClient.bindToApplicationContext(applicationContext).build();

        response = client.get()
                .uri("/test")
                .exchange();
    }

    @Then("I receive exactly these users in any order:")
    public void assertLocateUsersResponse(List<UserDetails> expected) {
        response.expectBody(new ParameterizedTypeReference<List<UserDetails>>() {})
                .consumeWith(r -> assertThat(r.getResponseBody()).containsExactly(expected.toArray(new UserDetails[0])));
    }

    @DataTableType
    public UserDetails userDetails(Map<String, String> row) {
        return new UserDetails(row.get("first_name"), row.get("last_name"));
    }
}
