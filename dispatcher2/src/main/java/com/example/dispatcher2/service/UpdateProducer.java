package com.example.dispatcher2.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateProducer {
    void produce (String rabbitQueue, Update update);
}
