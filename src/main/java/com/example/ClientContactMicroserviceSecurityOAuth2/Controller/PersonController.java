package com.example.ClientContactMicroserviceSecurityOAuth2.Controller;



import com.example.ClientContactMicroserviceSecurityOAuth2.Model.Person;
import com.example.ClientContactMicroserviceSecurityOAuth2.Model.ResultAuth;
import jakarta.annotation.PostConstruct;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class PersonController {

    private final RestTemplate restTemplate;
    String urlService = "http://localhost:8500";
    String urlKeycloak = "http://localhost:8080/realms/ContactsRealm/protocol/openid-connect/token";
    final String USERNAME = "admin";
    final String PASSWORD = "admin";
    final String CLIENT_ID = "login";
    final String GRANT_TYPE="password";
    HttpHeaders httpHeadersService = new HttpHeaders();

    @PostConstruct
    public void authenticate(){
        HttpHeaders headersKeycloak = new HttpHeaders();
        headersKeycloak.add("Content-type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> authData = new LinkedMultiValueMap<>();
        authData.add("client_id", CLIENT_ID);
        authData.add("username", USERNAME);
        authData.add("password", PASSWORD);
        authData.add("grant_type", GRANT_TYPE);
        ResponseEntity<ResultAuth> response = restTemplate.exchange(
                urlKeycloak,
                HttpMethod.POST,
                new HttpEntity<MultiValueMap<String, String>>(authData, headersKeycloak),
                ResultAuth.class);
        httpHeadersService.add("Authorization", "Bearer "
                + response.getBody().getAccess_token());

        System.out.println("Token:" + response.getBody().getAccess_token());
    }

    public PersonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/people/{name}/{email}/{age}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> addPerson(@PathVariable(name = "name")String name,
                                  @PathVariable(name = "email")String email,
                                  @PathVariable(name = "age") int age){
        Person person = new Person(name, email, age);
        restTemplate.exchange(urlService + "/contacts", HttpMethod.POST, new HttpEntity<Person>(person, httpHeadersService), String.class);
        Person[] people = restTemplate.exchange(
                urlService + "/contacts", HttpMethod.GET, new HttpEntity<>(httpHeadersService), Person[].class).getBody();
        return Arrays.asList(people);
    }
}
