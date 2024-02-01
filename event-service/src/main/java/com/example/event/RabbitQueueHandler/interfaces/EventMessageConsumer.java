package com.example.event.RabbitQueueHandler.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface EventMessageConsumer {
    void consume(SendMessage sendMessage);
}
