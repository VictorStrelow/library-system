package com.ctw.strelow.mapper;

import com.ctw.strelow.dto.livro.LivroRequestDTO;
import com.ctw.strelow.dto.livro.LivroResponseDTO;
import com.ctw.strelow.model.Livro;

public class LivroMapper {

    public static Livro toModel(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setAno_publicacao(dto.getAno_publicacao());
        return livro;
    }

    public static LivroResponseDTO toResponseDTO(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno_publicacao()
        );
    }

}