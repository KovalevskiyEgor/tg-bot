package com.example.event.RabbitQueueHandler;


import com.example.event.enums.Queues;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Log4j2
@RequiredArgsConstructor
public class EventQueueProducerImpl {
    private final RabbitTemplate rabbitTemplate;

    public void sendAllEvents(String queue, ArrayList<String> events){
        log.info("Я получил в EVENTQUEUE PRODUCER массив равен ="+events.size());
        // Логирование для отслеживания данных перед отправкой сообщения в очередь
        log.info("Sending message to GET_ALL_EVENTS_ANSWER queue. Type of message: " + events.getClass().getName());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(events);
            rabbitTemplate.convertAndSend(queue, jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



    }

    public void sendAddEventAnswer(String save) {
        rabbitTemplate.convertAndSend("ADD_EVENT_ANSWER",save);
    }

    public void sendApproveOrRejectEventAnswer(String approveOrNot) {
        rabbitTemplate.convertAndSend(Queues.ACCEPT_OR_REJECT_EVENT_ANSWER.toString(), approveOrNot);
    }

    public void sendEventId(long eventId) {
        log.info("sendEventId; eventId = "+ eventId);
        rabbitTemplate.convertAndSend(Queues.SEND_EVENT_ID.toString(), eventId);
    }
}
