package com.financial.self.models.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.financial.self.models.entity.Supplier;
import com.financial.self.models.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.oas.annotations.media.Schema;



@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
@Schema(description = "Data Transfer Object for Product")
public class ProductCreateRequest {

    @NotBlank(message = "ID do produto deve ser fornecido")
    @Schema(description = "Identificador único do produto", example = "P12345")
    private String idProduct;

    @NotBlank(message = "ID da empresa deve ser fornecido")
    @Schema(description = "Identificador da empresa associada ao produto", example = "E56789")
    private String idCompany;

    @NotNull(message = "Data de criação deve ser fornecida")
    @Schema(description = "Data de criação do produto", example = "2023-01-15")
    private LocalDate creationDate;

    @NotNull(message = "Categoria deve ser fornecida")
    @Size(min = 1, message = "Pelo menos uma categoria deve ser fornecida")
    @Schema(description = "Lista de categorias associadas ao produto", example = "[\"Eletrônicos\", \"Computadores\"]")
    private List<String> category;

    @NotBlank(message = "Nome do produto deve ser fornecido")
    @Schema(description = "Nome do produto", example = "Laptop Gamer")
    private String productName;

    @NotBlank(message = "Descrição do produto deve ser fornecida")
    @Schema(description = "Descrição do produto", example = "Laptop com alto desempenho para jogos")
    private String description;

    @NotNull(message = "Custo deve ser fornecido")
    @Positive(message = "Custo deve ser um valor positivo")
    @Schema(description = "Custo do produto", example = "2500.00")
    private BigDecimal cost;

    @Schema(description = "Imagem do produto em formato binário")
    private byte[] image;

    @NotNull(message = "Preço de venda deve ser fornecido")
    @Positive(message = "Preço de venda deve ser um valor positivo")
    @Schema(description = "Preço de venda do produto", example = "3000.00")
    private BigDecimal salePrice;

    @NotNull(message = "Validade deve ser fornecida")
    @Schema(description = "Data de validade do produto", example = "2024-12-31")
    private LocalDate expirationDate;

    @NotBlank(message = "Status deve ser fornecido")
    @Schema(description = "Estado atual do produto", example = "Ativo")
    private Status status;

    @NotNull(message = "Quantidade deve ser fornecida")
    @Positive(message = "Quantidade deve ser um valor positivo")
    @Schema(description = "Quantidade disponível do produto", example = "50")
    private Integer quantity;

    @NotNull(message = "Fornecedor deve ser fornecido")
    @Schema(description = "Informações sobre o fornecedor do produto")
    private Supplier supplier;

}
