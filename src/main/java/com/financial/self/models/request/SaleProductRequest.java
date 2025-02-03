package com.financial.self.models.request;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@Schema(title = "SaleProductRequest", accessMode = Schema.AccessMode.WRITE_ONLY)
public class SaleProductRequest {

    @NotBlank(message = "ID do produto deve ser fornecido")
    @Schema(description = "Identificador único do produto", example = "P12345")
    private String productId;

    @NotNull(message = "Quantidade deve ser fornecida")
    @Positive(message = "Quantidade deve ser um valor positivo")
    @Schema(description = "Quantidade disponível do produto", example = "50")
    private Integer quantity;
}
