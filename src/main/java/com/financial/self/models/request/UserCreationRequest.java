package com.financial.self.models.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.financial.self.models.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Schema(title = "UserCreationRequest", accessMode = Schema.AccessMode.WRITE_ONLY)
public class UserCreationRequest {

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be of valid format")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Email ID of user", example = "hardik.behl7444@gmail.com")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 5, message = "Password length must be at least 6 characters long")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Secure password to enable user login", example = "somethingSecure")
    private String password;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Unique identifier for the user", example = "U12345")
    private String idUser;


    @NotBlank(message = "Name must not be empty")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Full name of the user", example = "Hardik Behl")
    private String name;

    @NotBlank(message = "CPF must not be empty")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Brazilian CPF (Cadastro de Pessoas Físicas)", example = "123.456.789-09")
    private String cpf;

    @NotNull(message = "Date of birth must not be empty")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Date of birth of the user", example = "1990-01-01")
    private LocalDate birthDate;

    @NotBlank(message = "Phone number must not be empty")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "User's phone number", example = "(11) 91234-5678")
    private String phone;

    @Schema(description = "Date and time when the user was created", example = "2023-01-01T10:15:30")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Schema(description = "Date and time of the last update to the user's information", example = "2023-01-10T12:45:00")
    private LocalDateTime updateDate = LocalDateTime.now();

    @Schema(description = "Current status of the user", example = "ACTIVE")
    private UserStatus status = UserStatus.ACTIVE;
}
