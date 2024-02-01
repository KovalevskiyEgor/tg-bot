package com.example.dispatcher2.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandsList {
    Map<String, String> profile = new HashMap<>();
    Map<String, String> events = new HashMap<>();

    {
        profile.put("/мое_имя"," - изменить свое имя");
        profile.put("/мой_город"," - изменить свой город");
        profile.put("/мои_мероприятия", " - список мероприятий на которые вы собираетесь пойти");
    }

    {
        events.put("/мероприятия", "- список всех мероприятий");
    }
    StringBuilder helloList2 = new StringBuilder();
    StringBuilder helloList = new StringBuilder();

    {
        helloList.append("Приветствую тебя, дорогой друг, добро пожаловать в мир самых ярких и запоминающихся событий. Я - телеграм-бот который будет тебе рассказывать о ближайших событиях в твоем городе и не только \n Давай с тобой познакомимся, как тебя зовут? ");

    }

}
