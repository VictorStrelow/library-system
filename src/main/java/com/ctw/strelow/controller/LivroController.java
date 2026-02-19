package com.ctw.strelow.controller;

import com.ctw.strelow.model.Livro;
import com.ctw.strelow.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public ResponseEntity<?> postLivro(@RequestBody Livro livro) {
        try {
            livroService.save(livro);
            return new ResponseEntity<>(livro, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco: "+ e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Livro>> getAll() {
        try {
            List<Livro> livros = livroService.findAll();
            return ResponseEntity.ok(livros);

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            Livro livro = livroService.findById(id);
            return ResponseEntity.ok(livro);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putLivro(@PathVariable int id, @RequestBody Livro livro) {
        try {
            livro.setId(id);
            livroService.update(livro);
            return ResponseEntity.ok(livro);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLivro(@PathVariable int id) {
        try {
            livroService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}