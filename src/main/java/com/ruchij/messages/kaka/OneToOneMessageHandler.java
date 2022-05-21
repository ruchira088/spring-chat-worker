package com.ruchij.messages.kaka;

import com.ruchij.avro.chat.OneToOneMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OneToOneMessageHandler {

    @KafkaListener(topics = "one-to-one")
    public void consume(ConsumerRecord<String, OneToOneMessage> consumerRecord) {
        System.out.println(consumerRecord.value().getMessage());
    }

}
