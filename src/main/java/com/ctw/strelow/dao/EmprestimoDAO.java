package com.ctw.strelow.dao;

import com.ctw.strelow.model.Emprestimo;
import com.ctw.strelow.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmprestimoDAO {

    public Emprestimo save(Emprestimo emprestimo) throws SQLException {
        String query = "INSERT INTO emprestimo (livro_id, usuario_id, data_emprestimo) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, emprestimo.getLivro_id());
            stmt.setInt(2, emprestimo.getUsuario_id());
            stmt.setDate(3, Date.valueOf(emprestimo.getData_emprestimo()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                emprestimo.setId(rs.getInt(1));
            }
        }

        return emprestimo;
    }

    public List<Emprestimo> findAll() throws SQLException {
        String query = "SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao FROM emprestimo";

        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                emprestimos.add(ResultSetToEmprestimo(rs));
            }
        }

        return emprestimos;
    }

    public Emprestimo findById(int id) throws SQLException {
        String query = "SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao FROM emprestimo WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return ResultSetToEmprestimo(rs);
                }
            }
        }

        return null;
    }

    public List<Emprestimo> findByUser(int usuario_id) throws SQLException {
        String query = "SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao FROM emprestimo WHERE usuario_id = ?";

        List<Emprestimo> emprestimos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, usuario_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    emprestimos.add(ResultSetToEmprestimo(rs));
                }
            }
        }

        return emprestimos;
    }

    public void update(Emprestimo emprestimo) throws SQLException {
        String query = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, data_emprestimo = ?, data_devolucao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, emprestimo.getLivro_id());
            stmt.setLong(2, emprestimo.getUsuario_id());
            stmt.setDate(3, Date.valueOf(emprestimo.getData_emprestimo()));

            if (emprestimo.getData_devolucao() != null) {
                stmt.setDate(4, Date.valueOf(emprestimo.getData_devolucao()));

            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setLong(5, emprestimo.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM emprestimo WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean isLivroEmprestado(int livro_id) throws SQLException {
        String query = "SELECT COUNT(*) FROM emprestimo WHERE livro_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, livro_id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }

        return false;
    }

    // Metodo Auxiliar
    private Emprestimo ResultSetToEmprestimo(ResultSet rs) throws SQLException {
        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setId(rs.getInt("id"));
        emprestimo.setLivro_id(rs.getInt("livro_id"));
        emprestimo.setUsuario_id(rs.getInt("usuario_id"));
        emprestimo.setData_emprestimo(rs.getDate("data_emprestimo").toLocalDate());
        Date dataDevolucaoSql = rs.getDate("data_devolucao");
        if (dataDevolucaoSql != null) {
            emprestimo.setData_devolucao(dataDevolucaoSql.toLocalDate());
        } else {
            emprestimo.setData_devolucao(null);
        }

        return emprestimo;
    }

}