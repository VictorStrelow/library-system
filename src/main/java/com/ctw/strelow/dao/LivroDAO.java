package com.ctw.strelow.dao;

import com.ctw.strelow.model.Livro;
import com.ctw.strelow.utils.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroDAO {

    public Livro save(Livro livro) throws SQLException {
        String query = "INSERT INTO livro (titulo, autor, ano_publicacao) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno_publicacao());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                livro.setId(rs.getInt(1));
            }
        }

        return livro;
    }

    public List<Livro> findAll() throws SQLException {
        String query = "SELECT id, titulo, autor, ano_publicacao FROM livro";

        List<Livro> livros =new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                livros.add(ResultSetToLivro(rs));
            }
        }

        return livros;
    }

    public Livro findById(int id) throws SQLException {
        String query = "SELECT id, titulo, autor, ano_publicacao FROM livro WHERE id = ?";

        Livro livro = null;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return ResultSetToLivro(rs);
                }
            }
        }

        return null;
    }

    public void update(Livro livro) throws SQLException {
        String query = "UPDATE livro SET titulo = ?, autor = ?, ano_publicacao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno_publicacao());
            stmt.setInt(4, livro.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteById(int id) throws SQLException {
        String query = "DELETE FROM livro WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Metodo Auxiliar
    private Livro ResultSetToLivro(ResultSet rs) throws SQLException {
        Livro livro = new Livro();

        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setAno_publicacao(rs.getInt("ano_publicacao"));

        return livro;
    }

}