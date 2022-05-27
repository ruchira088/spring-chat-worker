package com.ruchij.services.api;

import com.ruchij.config.ApiServiceConfiguration;
import com.ruchij.jackson.CustomJackson2JsonDecoder;
import com.ruchij.jackson.CustomJackson2JsonEncoder;
import com.ruchij.jackson.JsonObjectMapper;
import com.ruchij.messages.kaka.models.OneToOne;
import com.ruchij.services.api.models.PushMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiServiceImpl implements ApiService {
    private final Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);

    private final WebClient webClient;

    public ApiServiceImpl(ApiServiceConfiguration apiServiceConfiguration, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
            .baseUrl(apiServiceConfiguration.getServiceUrl())
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiServiceConfiguration.getAuthenticationToken()))
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(new CustomJackson2JsonDecoder(JsonObjectMapper.objectMapper));
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(new CustomJackson2JsonEncoder(JsonObjectMapper.objectMapper));
            })
            .build();
    }

    @Override
    public Mono<Boolean> push(OneToOne oneToOne) {
        logger.info("Pushing messageId=%s to receiverId=%s".formatted(oneToOne.messageId(), oneToOne.receiverId()));

        return webClient.post()
            .uri("/push")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(new PushMessageRequest<>(oneToOne.receiverId(), oneToOne)), PushMessageRequest.class)
            .retrieve()
            .toBodilessEntity()
            .map(responseEntity -> responseEntity.getStatusCode().is2xxSuccessful())
            .doOnNext(result -> logger.info("Push result: messageId=%s, receiverId=%s, result=%s".formatted(oneToOne.messageId(), oneToOne.receiverId(), result)))
            .doOnError(throwable -> logger.error("Failed to push: messageId=%s, receiverId=%s".formatted(oneToOne.messageId(), oneToOne.receiverId()), throwable));
    }
}
