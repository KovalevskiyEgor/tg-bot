package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.example.service.ConsumerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.*;

@Service
@RequiredArgsConstructor
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final ProducerServiceImpl producerService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE : text message recieved");

        Message message = update.getMessage();
        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("Hello from node");

        producerService.producerAnswer(sendMessage);
    }

    @Override
    @RabbitListener(queues = DOC_MESSAGE_UPDATE)
    public void consumeDocMessageUpdates(Update update) {
        log.debug("NODE : doc message recieved");
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE : photo message recieved");
    }
}
