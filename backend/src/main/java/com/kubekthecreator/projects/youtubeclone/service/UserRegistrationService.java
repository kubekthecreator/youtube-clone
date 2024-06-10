package com.kubekthecreator.projects.youtubeclone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubekthecreator.projects.youtubeclone.dto.UserInfoDto;
import com.kubekthecreator.projects.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userinfoEndpoint;

    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;

    public void registerUser(String tokenValue) {
        WebClient webClient = webClientBuilder.build();

        try {
            String body = webClient.get()
                    .uri(userinfoEndpoint)
                    .headers(headers -> headers.setBearerAuth(tokenValue))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);

            userRepository.save(userInfoDto.asEntity());
        } catch (WebClientResponseException e) {
            throw new RuntimeException("Error occurred while calling userinfo endpoint", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error occurred while processing JSON response", e);
        }
    }
}
