package bpdtsdwp;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDetails(
        long id,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName) {
}
