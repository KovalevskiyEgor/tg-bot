package com.example.dispatcher2.service;

import com.example.dispatcher2.model.UserEntity;
import com.example.dispatcher2.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Data
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;


    public String setName(Long id, String name){
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);

        try {
            userRepository.save(user);
        }catch (Exception e){

        }

        return "Ваше имя было успешно изменено на "+name;

    }
}
