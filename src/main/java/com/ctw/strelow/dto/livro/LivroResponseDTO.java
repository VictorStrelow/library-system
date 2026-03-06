package com.ctw.strelow.dto.livro;

public record LivroResponseDTO(
        int id,
        String titulo,
        String autor,
        int ano_publicacao
) {}