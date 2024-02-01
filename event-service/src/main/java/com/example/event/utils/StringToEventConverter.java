package com.example.event.utils;

import com.example.event.model.Event;
import com.example.event.service.EventService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
@Log4j2
public class StringToEventConverter {
    private final EventService eventService;
    /*
    Нзвание

    Описани описание описание описание описание описание описание

    Место проведения: город Ывыф
    Адрес: фыв
    Стоимость: 2131
    Дата проведения: 23.01.2023
    */

    public Event convert(String event) throws ArrayIndexOutOfBoundsException{
        String[] lines = event.split(";");

        String title = lines[0].substring(lines[0].indexOf(":")+1).trim();
        String description = lines[1].substring(lines[1].indexOf(":")+1).trim();
        String city = lines[2].substring(lines[2].indexOf(":")+1).trim();
        String street = lines[3].substring(lines[3].indexOf(":")+1).trim();
        String price = lines[4].substring(lines[4].indexOf(":")+1).trim();
        String date = lines[5].substring(lines[5].indexOf(":")+1).trim();


        Event event1 = Event.builder()
                .title(title).description(description).city(city)
                .street(street).price(price).date(date).build();

        return event1;
    }
}
