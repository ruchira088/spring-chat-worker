package com.ruchij.daos.message;

import com.ruchij.daos.message.models.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageDao {
    Mono<Integer> insert(Message message);

    Flux<Message> findByUserId(String userId);
}
