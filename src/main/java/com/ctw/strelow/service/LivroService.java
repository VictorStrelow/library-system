package com.ctw.strelow.service;

import com.ctw.strelow.dto.livro.LivroRequestDTO;
import com.ctw.strelow.dto.livro.LivroResponseDTO;
import com.ctw.strelow.mapper.LivroMapper;
import com.ctw.strelow.model.Livro;
import com.ctw.strelow.dao.LivroDAO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroDAO livroDAO;

    private LivroService(LivroDAO repository) {
        this.livroDAO = repository;
    }

    public LivroResponseDTO save(LivroRequestDTO livroRequestDTO) throws SQLException {
        Livro livro = LivroMapper.toModel(livroRequestDTO);
        validarLivro(livro);

        Livro salvo = livroDAO.save(livro);
        return LivroMapper.toResponseDTO(salvo);
    }

    public List<LivroResponseDTO> findAll() throws SQLException {
        return livroDAO.findAll()
                .stream()
                .map(LivroMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO findById(int id) throws SQLException {
        Livro livro = livroDAO.findById(id);

        if (livro == null) {
            throw new RuntimeException("Livro não encontrado para o ID: " + id);
        }

        return LivroMapper.toResponseDTO(livro);
    }

    public LivroResponseDTO update(int id, LivroRequestDTO livroRequestDTO) throws SQLException {
        if (livroDAO.findById(id) == null) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }

        Livro livro = LivroMapper.toModel(livroRequestDTO);
        livro.setId(id);
        validarLivro(livro);
        livroDAO.update(livro);

        return LivroMapper.toResponseDTO(livro);
    }

    public void deleteById(int id) throws SQLException {
        livroDAO.deleteById(id);
    }

    // Métodos de Validação Auxiliares
    private void validarLivro(Livro livro) throws SQLException {
        // Validação de campos obrigatórios
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }

        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório");
        }

        // Validação de Ano
        int anoAtual = LocalDate.now().getYear();
        if (livro.getAno_publicacao() > anoAtual) {
            throw new IllegalArgumentException("o ano de publicação (" + livro.getAno_publicacao() + ") não pode ser no futuro.");
        }

        if (livro.getAno_publicacao() < 1450) {
            throw new IllegalArgumentException("Ano de publicação inválido para o acervo.");
        }

        // Validação de Tamanho
        if (livro.getTitulo().length() > 150) {
            throw new IllegalArgumentException("O título não pode exceder 150 caracteres.");
        }
    }

}