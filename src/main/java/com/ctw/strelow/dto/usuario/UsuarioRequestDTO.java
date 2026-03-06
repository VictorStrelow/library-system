package com.ctw.strelow.dto.usuario;

import jakarta.validation.constraints.*;

public record UsuarioRequestDTO(

        @NotBlank(message = "O nome é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório!")
        @Email(message = "E-mail inválido.")
        String email

) {}