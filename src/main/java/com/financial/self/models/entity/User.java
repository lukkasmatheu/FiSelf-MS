package com.financial.self.models.entity;

import com.financial.self.models.request.UserCreationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final String ENTITY_NAME = "user";

    public static String name() {
        return ENTITY_NAME;
    }

    private String idUser;

    private String email;

    private String name;

    private String cpf;

    private LocalDate birthDate;

    private String phone;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

    private String status;

    public static User fromRequest(UserCreationRequest request) {
        return User.builder()
                .idUser(request.getIdUser())
                .email(request.getEmail())
                .name(request.getName())
                .cpf(request.getCpf())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .creationDate(request.getCreationDate())
                .updateDate(request.getUpdateDate())
                .status(request.getStatus())
                .build();
    }
}