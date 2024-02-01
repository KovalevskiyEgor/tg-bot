package com.example.RabbitQueueHandler.interfaces;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface UserMessageConsumer {
    void consume(SendMessage sendMessage);
}
