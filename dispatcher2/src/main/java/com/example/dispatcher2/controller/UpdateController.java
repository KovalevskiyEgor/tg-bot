package com.example.dispatcher2.controller;

import com.example.dispatcher2.service.UserService;
import com.example.dispatcher2.service.impl.UpdateProducerImpl;
import com.example.dispatcher2.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.example.model.RabbitQueue.*;


@Component
@Log4j
@RequiredArgsConstructor
public class UpdateController {
    private TelegramBot telegramBot;
    private final MessageUtils messageUtils;
    private final UpdateProducerImpl updateProducer;
    private final UserService userService;

    public void registerBot(TelegramBot telegramBot){
        this.telegramBot=telegramBot;
    }

    public void processUpdate(Update update){
        if(update==null){
            log.error("recieved update is null");
            return;
        }

        if(update.getMessage()!=null){
            distributeMessageByType(update);
        }else {
            log.error("recieves unsupported message type");
        }
    }

    private void distributeMessageByType(Update update) {
        Message message = update.getMessage();

        if(message.getText().equals("/start")){

        }

        else if(message.getPhoto()!=null){
            processPhotoMessage(update);
        } 
        else if (message.getDocument()!=null) {
            processDocumentMessage(update);
        }
        else {
            setUnsupportedMessageTypeView(update);
        }
    }

    private void processStartMessage(Update update) {
        //telegramBot.sendAnswerMessage();
    }

    private void setUnsupportedMessageTypeView(Update update) {
        SendMessage sendMessage = messageUtils.generateSendMessage(update, "Неподдерживаемый тип данных");

        setView(sendMessage);
    }

    public void setView(SendMessage sendMessage) {
        telegramBot.sendAnswerMessage(sendMessage);
    }


    private void processDocumentMessage(Update update) {
        updateProducer.produce(DOC_MESSAGE_UPDATE,update);
        setFileIsReceivedView(update);
    }

    private void processPhotoMessage(Update update) {
        updateProducer.produce(PHOTO_MESSAGE_UPDATE,update);
        setFileIsReceivedView(update);
    }

    private void processTextMessage(Update update) {
        updateProducer.produce(TEXT_MESSAGE_UPDATE,update);
        setFileIsReceivedView(update);
    }

    private void setFileIsReceivedView(Update update) {
        SendMessage sendMessage = messageUtils.generateSendMessage(update,
                "Файл получен! Обработка...");
        setView(sendMessage);
    }


}
