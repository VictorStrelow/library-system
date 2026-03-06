package com.ctw.strelow.dto.emprestimo;

import java.time.LocalDate;

public record EmprestimoResponseDTO(
        int id,
        int livro_id,
        int usuario_id,
        LocalDate data_emprestimo,
        LocalDate data_devolucao
) {}