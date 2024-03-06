package com.example.ClientContactMicroserviceSecurityOAuth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientContactMicroserviceSecurityOAuth2Application {

	public static void main(String[] args) {
		SpringApplication.run(ClientContactMicroserviceSecurityOAuth2Application.class, args);
		System.out.println(SpringVersion.getVersion());
	}

	@Bean
	public RestTemplate template(){
		BasicAuthenticationInterceptor interceptor;
		interceptor = new BasicAuthenticationInterceptor("admin", "admin");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(interceptor);
		return restTemplate;
	}

}
