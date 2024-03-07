package edu.pet.tasktrackerapi.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class HHConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders hhApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "APPLNOJCO8HBV7V45E9CKCOJTVTI8258PMPIBGQ8MU3DLAO1D7N687VE48B7INK8");
        return headers;
    }
}