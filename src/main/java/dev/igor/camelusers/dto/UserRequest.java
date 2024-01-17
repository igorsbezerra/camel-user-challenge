package dev.igor.camelusers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("document")
    private String document;
    @JsonProperty("address")
    private String address;
    @JsonProperty("password")
    private String password;
}
