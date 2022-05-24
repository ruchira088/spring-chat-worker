package com.ruchij.messages.kaka;

import com.ruchij.avro.chat.OneToOneMessage;
import com.ruchij.daos.message.MessageDao;
import com.ruchij.messages.kaka.models.OneToOne;
import com.ruchij.services.api.ApiService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OneToOneMessageHandler {

    private final ApiService apiService;
    private final MessageDao messageDao;

    public OneToOneMessageHandler(ApiService apiService, MessageDao messageDao) {
        this.apiService = apiService;
        this.messageDao = messageDao;
    }

    @KafkaListener(topics = "one-to-one")
    public void handle(ConsumerRecord<String, OneToOneMessage> consumerRecord) {
        OneToOne oneToOne = OneToOne.fromMessage(consumerRecord.value());

        Mono.zip(apiService.push(oneToOne), messageDao.insert(oneToOne.toMessage())).block();
    }

}
