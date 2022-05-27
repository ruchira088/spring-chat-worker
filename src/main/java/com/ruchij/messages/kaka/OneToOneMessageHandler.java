package com.ruchij.messages.kaka;

import com.ruchij.avro.chat.OneToOneMessage;
import com.ruchij.daos.message.MessageDao;
import com.ruchij.messages.kaka.models.OneToOne;
import com.ruchij.services.api.ApiService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OneToOneMessageHandler {
    private final Logger logger = LoggerFactory.getLogger(OneToOneMessageHandler.class);

    private final ApiService apiService;
    private final MessageDao messageDao;

    public OneToOneMessageHandler(ApiService apiService, MessageDao messageDao) {
        this.apiService = apiService;
        this.messageDao = messageDao;
    }

    @KafkaListener(topics = "one-to-one")
    public void handle(ConsumerRecord<String, OneToOneMessage> consumerRecord) {
        OneToOne oneToOne = OneToOne.fromMessage(consumerRecord.value());

        logger.info("Received OneToOneMessage: messageId=%s, senderId=%s, receiverId=%s".formatted(oneToOne.messageId(), oneToOne.senderId(), oneToOne.receiverId()));

        Mono.zip(apiService.push(oneToOne), messageDao.insert(oneToOne.toMessage())).block();
    }

}
