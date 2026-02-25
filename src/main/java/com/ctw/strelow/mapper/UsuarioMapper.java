package com.ctw.strelow.mapper;

import com.ctw.strelow.dto.usuario.UsuarioRequestDTO;
import com.ctw.strelow.dto.usuario.UsuarioResponseDTO;
import com.ctw.strelow.model.Usuario;

public class UsuarioMapper {

    public static Usuario toModel(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }

    public static UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

}