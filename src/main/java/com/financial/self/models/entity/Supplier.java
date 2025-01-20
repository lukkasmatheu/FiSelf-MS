package com.financial.self.models.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Supplier {
    @NotBlank(message = "ID do fornecedor deve ser fornecido")
    private String idSupplier;

    @NotBlank(message = "Nome do fornecedor deve ser fornecido")
    private String name;

    @NotBlank(message = "CNPJ deve ser fornecido")
    private String cnpj;

    @NotBlank(message = "Contato deve ser fornecido")
    private String contato;
}
