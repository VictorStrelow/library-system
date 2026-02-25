package com.ctw.strelow.service;

import com.ctw.strelow.dao.EmprestimoDAO;
import com.ctw.strelow.dto.emprestimo.EmprestimoRequestDTO;
import com.ctw.strelow.dto.emprestimo.EmprestimoResponseDTO;
import com.ctw.strelow.mapper.EmprestimoMapper;
import com.ctw.strelow.model.Emprestimo;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoDAO emprestimoDAO;

    public EmprestimoService(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public EmprestimoResponseDTO save(EmprestimoRequestDTO dto) throws SQLException {
        Emprestimo emprestimo = EmprestimoMapper.toModel(dto);
        emprestimo.setData_emprestimo(LocalDate.now());

        if (emprestimoDAO.isLivroEmprestado(emprestimo.getLivro_id())) {
            throw new IllegalStateException("Este livro já se encontra emprestado e ainda não foi devolvido.");
        }

        emprestimoDAO.save(emprestimo);
        return EmprestimoMapper.toResponseDTO(emprestimo);
    }

    public List<EmprestimoResponseDTO> findAll() throws SQLException {
        return emprestimoDAO.findAll()
                .stream()
                .map(EmprestimoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoResponseDTO fingById(int id) throws SQLException {
        Emprestimo emprestimo = emprestimoDAO.findById(id);

        if (emprestimo == null) {
            throw new RuntimeException("Empréstimo não encontrado.");
        }

        return EmprestimoMapper.toResponseDTO(emprestimo);
    }

    public List<EmprestimoResponseDTO> findByUser(int usuario_id) throws SQLException {
        return emprestimoDAO.findByUser(usuario_id)
                .stream()
                .map(EmprestimoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EmprestimoResponseDTO update(int id, EmprestimoRequestDTO dto) throws SQLException {
        Emprestimo emprestimo = EmprestimoMapper.toModel(dto);
        emprestimo.setId(id);

        emprestimoDAO.update(emprestimo);
        return EmprestimoMapper.toResponseDTO(emprestimo);
    }

    public void returnRegister(int id) throws SQLException {
        Emprestimo emprestimo = emprestimoDAO.findById(id);

        if (emprestimo == null) {
            throw new RuntimeException("Empréstimo não encontrado.");
        }

        emprestimo.setData_devolucao(LocalDate.now());
        emprestimoDAO.update(emprestimo);
    }

    public void deleteById(int id) throws SQLException {
        emprestimoDAO.deleteById(id);
    }

}