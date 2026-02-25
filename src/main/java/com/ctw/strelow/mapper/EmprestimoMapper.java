package com.ctw.strelow.mapper;

import com.ctw.strelow.dto.emprestimo.EmprestimoRequestDTO;
import com.ctw.strelow.dto.emprestimo.EmprestimoResponseDTO;
import com.ctw.strelow.model.Emprestimo;

public class EmprestimoMapper {

    public static Emprestimo toModel(EmprestimoRequestDTO dto) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro_id(dto.getLivro_id());
        emprestimo.setUsuario_id(dto.getUsuario_id());
        return emprestimo;
    }

    public static EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo) {
        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                emprestimo.getLivro_id(),
                emprestimo.getUsuario_id(),
                emprestimo.getData_emprestimo(),
                emprestimo.getData_devolucao()
        );
    }

}