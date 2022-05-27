package com.ruchij.daos.message.models;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Optional;

@Document
public class MongoMessage {
    private String messageId;
    private String senderId;
    private Instant sentAt;
    private String receiverId;
    private String groupId;
    private String content;
    private Instant deliveredAt;
    private Instant readAt;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public void setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Instant deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Instant getReadAt() {
        return readAt;
    }

    public void setReadAt(Instant readAt) {
        this.readAt = readAt;
    }

    public Message toMessage() {
        return new Message(
            getMessageId(),
            getSenderId(),
            new DateTime(getSentAt()),
            getReceiverId(),
            Optional.ofNullable(getGroupId()),
            getContent(),
            Optional.ofNullable(getDeliveredAt()).map(DateTime::new),
            Optional.ofNullable(getReadAt()).map(DateTime::new)
        );
    }

    public static MongoMessage fromMessage(Message message) {
        MongoMessage mongoMessage = new MongoMessage();

        mongoMessage.setMessageId(message.messageId());
        mongoMessage.setSenderId(message.senderId());
        mongoMessage.setSentAt(Instant.ofEpochMilli(message.sentAt().getMillis()));
        mongoMessage.setReceiverId(message.receiverId());
        mongoMessage.setGroupId(message.groupId().orElse(null));
        mongoMessage.setContent(message.content());
        mongoMessage.setDeliveredAt(message.deliveredAt().map(dateTime -> Instant.ofEpochMilli(dateTime.getMillis())).orElse(null));
        mongoMessage.setReadAt(message.readAt().map(dateTime -> Instant.ofEpochMilli(dateTime.getMillis())).orElse(null));

        return mongoMessage;
    }

}
