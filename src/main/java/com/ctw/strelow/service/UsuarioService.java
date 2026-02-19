package com.ctw.strelow.service;

import com.ctw.strelow.dao.UsuarioDAO;
import com.ctw.strelow.model.Livro;
import com.ctw.strelow.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario save(Usuario usuario) throws SQLException {
        validarUsuario(usuario);
        return usuarioDAO.save(usuario);
    }

    public List<Usuario> findAll() throws SQLException {
        return usuarioDAO.findAll();
    }

    public Usuario findById(int id) throws SQLException {
        Usuario usuario = usuarioDAO.findById(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado para o ID: " + id);
        }

        return usuario;
    }

    public void update(Usuario usuario) throws SQLException {
        if (usuarioDAO.findById(usuario.getId()) == null) {
            throw new IllegalArgumentException("Usuário com ID " + usuario.getId() + " não encontrado.");
        }

        validarUsuario(usuario);
        usuarioDAO.update(usuario);
    }

    public void deleteById(int id) throws SQLException {
        usuarioDAO.deleteById(id);
    }

    // Métodos de Validação Auxiliares
    private void validarUsuario(Usuario usuario) throws SQLException {
        // Validação de campos obrigatórios
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

}