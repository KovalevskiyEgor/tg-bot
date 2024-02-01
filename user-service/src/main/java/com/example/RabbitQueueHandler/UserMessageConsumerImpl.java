package com.example.RabbitQueueHandler;

import com.example.enums.Queues;
import com.example.enums.Role;
import com.example.enums.UserState;
import com.example.models.AppUser;
import com.example.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@RequiredArgsConstructor
@Component
@Log4j2
public class UserMessageConsumerImpl {
    private final UserService userService;
    private final UserQueueProducerImpl userQueueProducer;

    @RabbitListener(queues = "CHECK_IF_REGISTERED_AND_ACTIVATED")
    public void checkIfRegisteredAndActivated(String chatId) {
        userService.findByTelegramId(Long.valueOf(chatId));
    }

    @RabbitListener(queues = "REGISTER_USER")
    public void registerUser(Update update){
        log.info(update.getMessage().getChat().getUserName());

        AppUser user = new AppUser().builder().telegramUserId(update.getMessage().getChatId())
                                .isActivated(true)
                                        .username(update.getMessage().getChat().getUserName())
                .role(Role.USER)
                .status(UserState.REGISTER_STATE)
                                                .build();

        if(update.getMessage().getText()!=null&&!update.getMessage().getText().equals("/start")){
            user.setCity(update.getMessage().getText());
            user.setStatus(UserState.BASIC_STATE);
        }
        userService.save(user);
        log.info("Получено сообщение в сервисе юзер! Запрос = REGISTER_USER");
    }



    @RabbitListener(queues = "CHECK_ROLE")
    public void checkRole(long chatId){
        String role = userService.getRole(chatId);
        userQueueProducer.sendRole(Queues.CHECK_ROLE_ANSWER.toString(),role);
    }


    @RabbitListener(queues = "CHECK_STATUS")
    public void checkStatus(long chatId){
        String status = userService.getStatus(chatId);
        userQueueProducer.sendStatus(Queues.CHECK_STATUS_ANSWER.toString(), status);
    }

    @RabbitListener(queues = "SET_STATUS_ADD_EVENT")
    public void setStatusAddEvent(long chatId){
        userService.setStatus(chatId,UserState.ADD_EVENT_STATE.toString());
    }

    @RabbitListener(queues = "SET_STATUS_APPROVE_EVENT_STATE")
    public void setStatusApproveEvent(long chatId){
        userService.setStatus(chatId,UserState.APPROVE_EVENT_STATE.toString());
    }

    @RabbitListener(queues = "SET_STATUS_BASIC")
    public void setStatusBasic(long chatId){
        userService.setStatus(chatId,UserState.BASIC_STATE.toString());
    }

    @RabbitListener(queues = "SET_STATUS_REGISTER")
    public void setStatusRegister(long chatId){
        userService.setStatus(chatId,UserState.REGISTER_STATE.toString());
    }

    @RabbitListener(queues = "SET_STATUS_MODIFY_EVENT_STATE")
    public void setStatusModifyEvent(long chatId){
        userService.setStatus(chatId,UserState.MODIFY_EVENT_STATE.toString());
    }

    @RabbitListener(queues = "SET_STATUS_MODIFY_NAME_STATE")
    public void setStatusModifyName(long chatId){
        userService.setStatus(chatId,UserState.MODIFY_NAME_STATE.toString());
    }

    @RabbitListener(queues = "GET_USERNAME")
    public void getUsername(long chatId){
        String username = userService.getUsername(chatId);

        userQueueProducer.sendUsername("GET_USERNAME_ANSWER",username);
    }

    @RabbitListener(queues = "SET_NAME")
    public void setUsername(Update update){
        try {

            String answer = userService.setUsername(update.getMessage().getChat().getId(),update.getMessage().getText());
            userQueueProducer.sendUsername("SET_NAME_ANSWER",answer);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @RabbitListener(queues = "SET_STATUS_ADD_MY_EVENT")
    public void setStatusAddMyEvent(long chatId){
        userService.setStatus(chatId,UserState.ADD_MY_EVENT_STATE.toString());
    }




}
