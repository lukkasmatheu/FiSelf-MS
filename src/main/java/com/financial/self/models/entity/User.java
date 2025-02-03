package com.financial.self.models.entity;

import com.financial.self.models.request.UserCreationRequest;
import lombok.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private static final String ENTITY_NAME = "user";
    public static final ZoneId ZONE_BRASIL = ZoneId.of("America/Sao_Paulo");


    public static String name() {
        return ENTITY_NAME;
    }

    private String idUser;

    private String email;

    private String name;

    private String cpf;

    private Date birthDate;

    private String phone;

    private Date creationDate;

    private Date updateDate;

    private String status;

    public static User fromRequest(UserCreationRequest request) {
        return User.builder()
                .idUser(request.getIdUser())
                .email(request.getEmail())
                .name(request.getName())
                .cpf(request.getCpf())
                .birthDate(Date.from(request.getBirthDate().atStartOfDay(ZONE_BRASIL).toInstant()))
                .phone(request.getPhone())
                .creationDate(Date.from(request.getCreationDate().atZone(ZONE_BRASIL).toInstant()))
                .updateDate(Date.from(request.getUpdateDate().atZone(ZONE_BRASIL).toInstant()))
                .status(request.getStatus().name())
                .build();
    }
}