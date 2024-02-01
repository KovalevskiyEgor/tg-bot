package com.example.dispatcher2.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import com.example.dispatcher2.service.UpdateProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Log4j
@RequiredArgsConstructor
public class UpdateProducerImpl implements UpdateProducer {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void produce(String rabbitQueue, Update update) {
        log.debug(update.getMessage().getText());
        rabbitTemplate.convertAndSend(rabbitQueue,update);
    }
}
