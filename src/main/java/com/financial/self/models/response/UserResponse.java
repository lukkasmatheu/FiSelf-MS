package com.financial.self.models.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Getter
@Builder
@Jacksonized
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Schema(title = "UserResponse", accessMode = Schema.AccessMode.READ_ONLY)
public class UserResponse {
    private String idUser;
    private String refreshToken;
    private String accessToken;
    private String expiresIn;
    private String email;
    private String name;
    private String cpf;
    private LocalDate birthDate;
    private String phone;
}