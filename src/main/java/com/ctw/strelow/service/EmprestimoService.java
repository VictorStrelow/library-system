package com.ctw.strelow.service;

import com.ctw.strelow.dao.EmprestimoDAO;
import com.ctw.strelow.model.Emprestimo;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {

    private EmprestimoDAO emprestimoDAO;

    public EmprestimoService(EmprestimoDAO emprestimoDAO) {
        this.emprestimoDAO = emprestimoDAO;
    }

    public void save(Emprestimo emprestimo) throws SQLException {
        emprestimo.setData_emprestimo(LocalDate.now());

        if (emprestimoDAO.isLivroEmprestado(emprestimo.getLivro_id())) {
            throw new IllegalStateException("Este livro já se encontra emprestado e ainda não foi devolvido.");
        }

        emprestimoDAO.save(emprestimo);
    }

    public List<Emprestimo> findAll() throws SQLException {
        return emprestimoDAO.findAll();
    }

    public Emprestimo fingById(int id) throws SQLException {
        Emprestimo emprestimo = emprestimoDAO.findById(id);

        if (emprestimo == null) {
            throw new RuntimeException("Empréstimo não encontrado.");
        }

        return emprestimo;
    }

    public List<Emprestimo> findByUser(int usuario_id) throws SQLException {
        return emprestimoDAO.findByUser(usuario_id);
    }

    public void update(Emprestimo emprestimo) throws SQLException {
        emprestimoDAO.update(emprestimo);
    }

    public void returnRegister(int id) throws SQLException {
        Emprestimo emprestimo = fingById(id);

        emprestimo.setData_devolucao(LocalDate.now());
        emprestimoDAO.update(emprestimo);
    }

    public void deleteById(int id) throws SQLException {
        emprestimoDAO.deleteById(id);
    }

}