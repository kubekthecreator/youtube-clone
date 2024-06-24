package com.kubekthecreator.projects.youtubeclone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubekthecreator.projects.youtubeclone.dto.UserInfoDto;
import com.kubekthecreator.projects.youtubeclone.model.User;
import com.kubekthecreator.projects.youtubeclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    @Value("${auth0.userinfoEndpoint}")
    private String userinfoEndpoint;

    private final UserRepository userRepository;
    private final WebClient.Builder webClientBuilder;

    public Mono<String> registerUser(String tokenValue) {
        return webClientBuilder.build().get()
                .uri(userinfoEndpoint)
                .headers(headers -> headers.setBearerAuth(tokenValue))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::processUserInfo)
                .onErrorMap(WebClientResponseException.class,
                        e -> new RuntimeException("Error occurred while calling userinfo endpoint", e))
                .onErrorMap(JsonProcessingException.class,
                        e -> new RuntimeException("Error occurred while processing JSON response", e));
    }

    private Mono<String> processUserInfo(String body) {
        return Mono.fromCallable(() -> {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoDto userInfoDto = objectMapper.readValue(body, UserInfoDto.class);

            return userRepository.findBySub(userInfoDto.getSub())
                    .map(User::getId)
                    .orElseGet(() -> userRepository.save(userInfoDto.asEntity()).getId());
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
