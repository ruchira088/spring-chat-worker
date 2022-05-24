package com.ruchij.daos.message;

import com.ruchij.daos.message.models.Message;
import com.ruchij.daos.message.models.MongoMessage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MongoMessageDao extends ReactiveCrudRepository<MongoMessage, String>, MessageDao {
    @Override
    default Mono<Integer> insert(Message message) {
        return save(MongoMessage.fromMessage(message)).map(result -> 1);
    }

    @Override
    default Flux<Message> findByUserId(String userId) {
        return findBySenderIdOrReceiverIdOrderBySentAt(userId, userId).map(MongoMessage::toMessage);
    }

    Flux<MongoMessage> findBySenderIdOrReceiverIdOrderBySentAt(String senderId, String receiverId);
}
