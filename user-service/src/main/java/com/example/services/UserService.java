package com.example.event.service;

import com.example.event.enums.EventStatus;
import com.example.event.model.Event;
import com.example.event.repository.EventRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


import java.util.ArrayList;

@Service
@Data
@RequiredArgsConstructor
@Log4j2
public class EventService {
    private final EventRepository eventRepository;

    public ArrayList<Event> getAllEvents(){
        try {
            ArrayList<Event> arrayList = (ArrayList<Event>) eventRepository.findAll();
            return arrayList;
        }
        catch (Exception e){
            //TODO СВОЕ ИСКЛЮЧЕНИЕ
        }
        return new ArrayList<>();
    }

    public String save(Event event){
        try {
            eventRepository.save(event);

            return "Мероприятие успешно добавлено!";
        }catch (Exception e){
            //TODO СВОЕ ИСКЛЮЧЕНИЕ
            return "Мероприятие не добавлено";
        }
    }


    public ArrayList<Event> getAllAcceptedEvents() {
        try {
            ArrayList<Event> arrayList = (ArrayList<Event>) eventRepository.findAllByEventStatus(EventStatus.ACCEPTED);

            return arrayList;
        }
        catch (Exception e){
            log.info("CATCH");
            //TODO СВОЕ ИСКЛЮЧЕНИЕ
        }
        return new ArrayList<>();
    }

    public ArrayList<Event> getAllSuggestedEvents() {
        try {
            ArrayList<Event> arrayList = (ArrayList<Event>) eventRepository.findAllByEventStatus(EventStatus.SUGGESTED);
            return arrayList;
        }
        catch (Exception e){
            //TODO СВОЕ ИСКЛЮЧЕНИЕ
        }
        return new ArrayList<>();
    }

    public Event findByName(String name){
        name=name.replaceAll("\"", "");
        try {
            return eventRepository.findByTitle(name);
        }catch (Exception e){
            log.info("Мероприятия с таким именем нет");
        }
        return null;
    }

    public String approve(String eventName) {
        Event event = findByName(eventName);
        if(event!=null){
            event.setEventStatus(EventStatus.ACCEPTED);
            eventRepository.save(event);
            return "Мероприятие было принято вами!";
        }
        else {
            return "Мероприятия с таким названием нет, проверьте правильность введенных вами данных";
        }


    }

    public String delete(String eventName) {
        Event event = findByName(eventName);
        if(event!=null){
            event.setEventStatus(EventStatus.ACCEPTED);
            eventRepository.delete(event);
            return "Мероприятие было не принято вами";
        }
        else {
            return "Мероприятия с таким названием нет, проверьте правильность введенных вами данных";
        }
    }

    public ArrayList<Event> getAllEventsByIdList(ArrayList<Integer> idList) {
        ArrayList<Event> eventList =new ArrayList<>();

        for(Integer id: idList){

            eventList.add(eventRepository.getById(Long.valueOf(id)));
        }
        log.info(" я в getAllEventsByIdList eventId == ");
        return eventList;
    }

    public Long getEventIdByName(String eventName) {
        eventName=eventName.replaceAll("\"", "");
        try {
            Event event = findByName(eventName);
            log.info("eventName = "+ eventName);
            return event.getId();
        }catch (Exception e){
            e.printStackTrace();
            return -1l;
        }
    }

    public Event getById(long id){
        return eventRepository.findById(id).get();
    }
}
