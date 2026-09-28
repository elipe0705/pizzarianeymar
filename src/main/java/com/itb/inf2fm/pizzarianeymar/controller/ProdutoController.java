package com.itb.inf2fm.pizzarianeymar.controller;

import com.itb.inf2fm.pizzarianeymar.model.entity.Produto;
import com.itb.inf2fm.pizzarianeymar.model.services.ProdutoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoServices produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> findAll() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @PostMapping
    public ResponseEntity<Produto> save(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(produtoService.findById(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", "O id informado não é válido: " + id
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Produto não encontrado com o id: " + id
            ));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable String id, @RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(produtoService.update(Long.parseLong(id), produto));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", "O id informado não é válido: " + id
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Produto não encontrado com o id: " + id
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirProduto(@PathVariable String id) {
        try {
            produtoService.delete(Long.parseLong(id));
            return ResponseEntity.ok().body(Map.of(
                    "status", 200,
                    "message", "Produto excluído com sucesso"
            ));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", 400,
                    "error", "Bad Request",
                    "message", "O id informado não é válido: " + id
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", "Produto não encontrado com o id: " + id
            ));
        }
    }
}
