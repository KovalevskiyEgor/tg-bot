package com.example.RabbitQueueHandler;


import com.example.enums.Queues;
import com.example.models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
@RequiredArgsConstructor
public class UserQueueProducerImpl {
    private final RabbitTemplate rabbitTemplate;

    public void sendIfRegisteredAndActivated(String queue, boolean isRegisteredAndActivated) {

        rabbitTemplate.convertAndSend(queue,isRegisteredAndActivated);
    }

    public void sendRole(String queue, String role){
        rabbitTemplate.convertAndSend(queue, role);
    }

    public void sendAllEvents(String queue, List<Event> events){
        rabbitTemplate.convertAndSend(queue,events);
    }

    public void sendStatus(String queues, String status) {
        rabbitTemplate.convertAndSend(queues,status);
    }

    public void sendUsername(String queue, String answer) {
        rabbitTemplate.convertAndSend(queue,answer);
    }
}
