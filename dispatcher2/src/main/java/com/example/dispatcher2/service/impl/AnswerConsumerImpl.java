package com.example.dispatcher2.service.impl;

import com.example.dispatcher2.controller.UpdateController;
import lombok.RequiredArgsConstructor;

import com.example.dispatcher2.service.AnswerConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.example.model.RabbitQueue.ANSWER_MESSAGE;

@Service
@RequiredArgsConstructor
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;

    @RabbitListener(queues = ANSWER_MESSAGE)
    @Override
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }


}
