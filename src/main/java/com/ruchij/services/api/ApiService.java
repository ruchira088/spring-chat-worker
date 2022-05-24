package com.ruchij.services.api;

import com.ruchij.messages.kaka.models.OneToOne;
import reactor.core.publisher.Mono;

public interface ApiService {
    Mono<Boolean> push(OneToOne oneToOne);
}
