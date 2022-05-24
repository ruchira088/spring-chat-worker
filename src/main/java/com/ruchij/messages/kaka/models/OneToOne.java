package com.ruchij.messages.kaka.models;

import com.ruchij.avro.chat.OneToOneMessage;
import com.ruchij.daos.message.models.Message;
import org.joda.time.DateTime;

import java.util.Optional;

public record OneToOne(String messageId, String senderId, DateTime sentAt, String receiverId, String content) {
    public Message toMessage() {
        return new Message(
            messageId, senderId, sentAt, receiverId, Optional.empty(), content, Optional.empty(), Optional.empty()
        );
    }

    public static OneToOne fromMessage(OneToOneMessage oneToOneMessage) {
        return new OneToOne(
            oneToOneMessage.getMessageId().toString(),
            oneToOneMessage.getSenderId().toString(),
            new DateTime(oneToOneMessage.getSentAt().toEpochMilli()),
            oneToOneMessage.getReceiverId().toString(),
            oneToOneMessage.getMessage().toString()
        );
    }
}
