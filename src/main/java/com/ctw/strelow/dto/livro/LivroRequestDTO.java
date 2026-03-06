package com.ctw.strelow.dto.livro;

import jakarta.validation.constraints.*;

public record LivroRequestDTO(

        @NotBlank(message = "O título é obrigatório!")
        @Size(min = 1, max = 200, message = "O título deve ter entre 1 e 200 caracteres.")
        String titulo,

        @NotBlank(message = "O autor é obrigatório!")
        @Size(min = 3, max = 100, message = "O nome do autor deve ter entre 3 e 100 caracteres.")
        String autor,

        @Min(value = 1000, message = "O ano de publicação deve ser válido (mínimo 1000).")
        @Max(value = 2026, message = "O ano de publicação não pode ser no futuro.")
        int ano_publicacao

) {}