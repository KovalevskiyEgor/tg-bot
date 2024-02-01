package com.example.event.model;

import com.example.event.enums.EventStatus;

import lombok.*;


import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "Event")
@Builder
@AllArgsConstructor

public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String city;
    private String street;
    private String price;
    private String date;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    @Override
    public String toString() {
        return title+
                "\n"+description+
                "\nГород: "+city+
                "\nУлица: "+street+
                "\nСтоимость: " + price+ " рублей"+
                "\nДата проведения: "+date;
    }
}
