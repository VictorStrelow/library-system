package com.ctw.strelow.controller;

import com.ctw.strelow.dto.emprestimo.EmprestimoRequestDTO;
import com.ctw.strelow.dto.emprestimo.EmprestimoResponseDTO;
import com.ctw.strelow.model.Emprestimo;
import com.ctw.strelow.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<?> postEmprestimo(@RequestBody EmprestimoRequestDTO emprestimoRequestDTO) {
        try {
            return new ResponseEntity<>(emprestimoService.save(emprestimoRequestDTO), HttpStatus.CREATED);

        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro no banco de dados: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EmprestimoResponseDTO>> getAll() {
        try {
            return ResponseEntity.ok(emprestimoService.findAll());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(emprestimoService.fingById(id));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putEmprestimo(@PathVariable int id, @RequestBody EmprestimoRequestDTO dto) {
        try {
            return ResponseEntity.ok(emprestimoService.update(id, dto));

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<?> putDevolucao(@PathVariable int id) {
        try {
            emprestimoService.returnRegister(id);
            return ResponseEntity.ok("Devolução registrada com sucesso!");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmprestimo(@PathVariable int id) {
        try {
            emprestimoService.deleteById(id);
            return ResponseEntity.noContent().build();

        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}