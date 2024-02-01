package com.example.event.RabbitQueueHandler.interfaces;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface EventQueueProducer {
    void produce(String queue, Update update);
}
