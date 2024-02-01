package com.example.event.repository;

import com.example.event.enums.EventStatus;
import com.example.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {

    List<Event> findAllByCity(String city);
    List<Event> findAllByEventStatus(EventStatus status);
    List<Event> findAllByEventStatusAndCity(String status, String city);


    Event findByTitle(String name);
}
