package bpdtsdwp;

import com.fasterxml.jackson.annotation.JsonProperty;

record User(
        long id,
        @JsonProperty("first_name") String firstName,
        @JsonProperty("last_name") String lastName,
        String email,
        @JsonProperty("ip_address") String ipAddress,
        double latitude,
        double longitude) {
}
