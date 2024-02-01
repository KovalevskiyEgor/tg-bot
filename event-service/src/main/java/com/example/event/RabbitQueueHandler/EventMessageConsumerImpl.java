package com.example.event.RabbitQueueHandler;

import com.example.event.enums.EventStatus;
import com.example.event.enums.Queues;
import com.example.event.model.Event;
import com.example.event.service.EventService;
import com.example.event.utils.StringToEventConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
@Log4j2
public class EventMessageConsumerImpl {
    private final EventQueueProducerImpl eventQueueProducer;
    private final EventService eventService;
    private final StringToEventConverter eventConverter;

    @RabbitListener(queues = "GET_ALL_ACCEPTED_EVENTS")
    public void getAllAcceptedEvents(){
        log.info("я в event message consumer " );
        ArrayList<Event> list = eventService.getAllAcceptedEvents();

        list.forEach(event -> event.toString());
        ArrayList<String> stringEvent = new ArrayList<>();

        for(Event event: list){
            stringEvent.add(event.toString());
        }

        log.info("я в event message consumer массив равен = " +stringEvent.size());

        eventQueueProducer.sendAllEvents(Queues.GET_ALL_ACCEPTED_EVENTS_ANSWER.toString(), stringEvent);
    }
    @RabbitListener(queues = "GET_ALL_SUGGESTED_EVENTS")
    public void getAllSuggestedEvents(){

        ArrayList<Event> list = eventService.getAllSuggestedEvents();
        list.forEach(event -> event.toString());
        ArrayList<String> stringEvent = new ArrayList<>();

        for(Event event: list){
            stringEvent.add(event.toString());
        }

        log.info("я в event message consumer массив равен = " +stringEvent.size());

        eventQueueProducer.sendAllEvents(Queues.GET_ALL_ACCEPTED_EVENTS_ANSWER.toString(), stringEvent);
    }

    @RabbitListener(queues = "ADD_EVENT")
    public void addEvent(String stringEvent){
        Event event;
        try {
            event = eventConverter.convert(stringEvent);
        }catch (Exception e){
            //e.printStackTrace();
            eventQueueProducer.sendAddEventAnswer("Вы неправильно описали мероприятие, посмотрите на шаблон внимательно и повторите попытку");
            return;
        }
        event.setEventStatus(EventStatus.ACCEPTED);
        eventQueueProducer.sendAddEventAnswer(eventService.save(event));
    }

    @RabbitListener(queues = "ADD_SUGGESTED_EVENT")
    public void addSuggestedEvent(String stringEvent){
        Event event;

        try {
            event = eventConverter.convert(stringEvent);
        }catch (Exception e){
            //e.printStackTrace();
            eventQueueProducer.sendAddEventAnswer("Вы неправильно описали мероприятие, посмотрите на шаблон внимательно и повторите попытку");
            return;
        }

        event.setEventStatus(EventStatus.SUGGESTED);
        eventService.save(event);
        eventQueueProducer.sendAddEventAnswer("Предложеное вами мероприятие будет рассмотрено нашими модераторами...");
    }

    @RabbitListener(queues = "ACCEPT_OR_REJECT_EVENT")
    public void acceptOrRejectEvent(String message){
        try {
            String eventName = message.substring(1,message.indexOf("-")).trim();
            log.info("eventName = "+eventName);

            String answer = message.substring(message.indexOf("-")+1,message.length()-1).trim().toLowerCase();
            log.info("answer = "+answer);

            if(answer.equals("нет")){
                eventQueueProducer.sendApproveOrRejectEventAnswer(eventService.delete(eventName));
            }
            else if(answer.equals("да")){
                eventQueueProducer.sendApproveOrRejectEventAnswer(eventService.approve(eventName));
            }
        }catch (Exception e){
            eventQueueProducer.sendApproveOrRejectEventAnswer("неправильный формат подтверждения, внимательни изучите пример и попробуйте еще раз. Если передумали нажмите /esc");
        }
    }

    @RabbitListener(queues = "GET_ALL_EVENTS_BY_ID")
    public void getAllEventsById(String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ArrayList<Integer> idList = objectMapper.readValue(jsonData, ArrayList.class);

            //ArrayList<Event> list = eventService.getAllEventsByIdList(idList);

            ArrayList<String> stringEvent = new ArrayList<>();

            for(Integer id: idList){
                stringEvent.add(eventService.getById(id).toString());
            }


            /*for(Event event: list){
                log.info(" я в getAllEventsById == " +event.getTitle());
                stringEvent.add(event.toString());
            }*/

            log.info("я в event message consumer массив равен = " +stringEvent.size());

            eventQueueProducer.sendAllEvents(Queues.GET_ALL_EVENTS_BY_ID_ANSWER.toString(), stringEvent);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @RabbitListener(queues = "CHECK_EVENT_ID")
    public void checkEventId(String eventName){
        log.info(" я в checkEventId, eventName = "+eventName);

        eventQueueProducer.sendEventId(eventService.getEventIdByName(eventName));
    }


}
