package com.example.RabbitQueueHandler.interfaces;


import org.telegram.telegrambots.meta.api.objects.Update;

public interface UserQueueProducer {
    void produce(String queue, Update update);
}
