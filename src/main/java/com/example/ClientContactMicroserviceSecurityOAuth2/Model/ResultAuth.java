package com.example.ClientContactMicroserviceSecurityOAuth2.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ResultAuth {

    @JsonProperty("access_token")//If we want to call our property by another name
    private String access_token;
}
