package com.example.models;

import com.example.enums.Queues;
import com.example.enums.Role;
import com.example.enums.UserState;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long telegramUserId;
    private String username;
    private String city;
    private Boolean isActivated;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserState status;

}
