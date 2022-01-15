package bpdtsdwp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDetails(
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName) {
}
