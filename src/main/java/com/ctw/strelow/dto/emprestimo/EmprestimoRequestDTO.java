package com.ctw.strelow.dto.emprestimo;

import jakarta.validation.constraints.Positive;

public record EmprestimoRequestDTO (

        @Positive(message = "O ID do livro deve ser positivo.")
        int livro_id,

        @Positive(message = "O ID do usuário deve ser positivo.")
        int usuario_id

) {}