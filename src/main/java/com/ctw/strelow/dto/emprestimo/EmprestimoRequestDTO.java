package com.ctw.strelow.dto.emprestimo;

public class EmprestimoRequestDTO {

    private int livro_id;
    private int usuario_id;

    // Construtor
    public EmprestimoRequestDTO() {}

    public EmprestimoRequestDTO(int livro_id, int usuario_id) {
        this.livro_id = livro_id;
        this.usuario_id = usuario_id;
    }

    // Getters e Setters
    public int getLivro_id() {
        return livro_id;
    }
    public void setLivro_id(int livro_id) {
        this.livro_id = livro_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }
    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

}