package com.ruchij.services.api.models;

public record PushMessageRequest<A>(String receiverId, A message) {}
