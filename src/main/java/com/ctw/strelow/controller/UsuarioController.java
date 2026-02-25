package com.ctw.strelow.controller;

import com.ctw.strelow.dto.emprestimo.EmprestimoResponseDTO;
import com.ctw.strelow.dto.usuario.UsuarioRequestDTO;
import com.ctw.strelow.dto.usuario.UsuarioResponseDTO;
import com.ctw.strelow.model.Emprestimo;
import com.ctw.strelow.service.EmprestimoService;
import com.ctw.strelow.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final EmprestimoService emprestimoService;

    public UsuarioController(UsuarioService usuarioService, EmprestimoService emprestimoService) {
        this.usuarioService = usuarioService;
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<?> postUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            return new ResponseEntity<>(usuarioService.save(usuarioRequestDTO), HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        try {
            return ResponseEntity.ok(usuarioService.findAll());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(usuarioService.findById(id));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putUsuario(@PathVariable int id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        try {
            return ResponseEntity.ok(usuarioService.update(id, usuarioRequestDTO));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable int id) {
        try {
            usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/emprestimos")
    public ResponseEntity<?> getEmprestimosDoUsuario(@PathVariable int id) {
        try {
            usuarioService.findById(id);
            List<EmprestimoResponseDTO> emprestimos = emprestimoService.findByUser(id);
            return ResponseEntity.ok(emprestimos);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno: " + e.getMessage());
        }
    }

}