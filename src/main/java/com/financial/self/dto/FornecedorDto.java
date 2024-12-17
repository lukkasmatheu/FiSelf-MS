package com.financial.self.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FornecedorDto {
    @NotBlank(message = "ID do fornecedor deve ser fornecido")
    private String idFornecedor;

    @NotBlank(message = "Nome do fornecedor deve ser fornecido")
    private String nome;

    @NotBlank(message = "CNPJ deve ser fornecido")
    private String cnpj;

    @NotBlank(message = "Contato deve ser fornecido")
    private String contato;
}
