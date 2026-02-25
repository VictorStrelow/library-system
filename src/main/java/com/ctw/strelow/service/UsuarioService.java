package com.ctw.strelow.service;

import com.ctw.strelow.dao.UsuarioDAO;
import com.ctw.strelow.dto.usuario.UsuarioRequestDTO;
import com.ctw.strelow.dto.usuario.UsuarioResponseDTO;
import com.ctw.strelow.mapper.UsuarioMapper;
import com.ctw.strelow.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public UsuarioResponseDTO save(UsuarioRequestDTO dto) throws SQLException {
        Usuario usuario = UsuarioMapper.toModel(dto);
        validarUsuario(usuario);

        Usuario salvo = usuarioDAO.save(usuario);
        return UsuarioMapper.toResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> findAll() throws SQLException {
        return usuarioDAO.findAll()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO findById(int id) throws SQLException {
        Usuario usuario = usuarioDAO.findById(id);

        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado para o ID: " + id);
        }

        return UsuarioMapper.toResponseDTO(usuario);
    }

    public UsuarioResponseDTO update(int id, UsuarioRequestDTO dto) throws SQLException {
        if (usuarioDAO.findById(id) == null) {
            throw new IllegalArgumentException("Usuário com ID " + id + " não encontrado.");
        }

        Usuario usuario = UsuarioMapper.toModel(dto);
        usuario.setId(id);
        validarUsuario(usuario);
        usuarioDAO.update(usuario);

        return UsuarioMapper.toResponseDTO(usuario);
    }

    public void deleteById(int id) throws SQLException {
        usuarioDAO.deleteById(id);
    }

    // Métodos de Validação Auxiliares
    private void validarUsuario(Usuario usuario) {
        // Validação de campos obrigatórios
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }

        if (usuario.getEmail() == null || !usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
    }

}