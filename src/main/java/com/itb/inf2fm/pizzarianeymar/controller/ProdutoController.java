package com.itb.inf2fm.pizzarianeymar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itb.inf2fm.pizzarianeymar.model.entity.Produto;
import com.itb.inf2fm.pizzarianeymar.model.services.ProdutoServices;


// ANOTAÇÕES PARA A CLASSE  dependência necessária -> spring-boot-starter-webmvc

// @Controller:     Sistema Web ( Sites em geral ) - Back-End + Front-End
// @RestController: Api  - Apenas Back-End

// ANOTAÇÕES PARA MÉTODOS dependência necessária -> spring-boot-starter-webmvc

// @GetMapping: Utilizado para "buscar" dados na API (Somente pesquisa)
// @PostMapping: Utilizado para "enviar" dados para API 
// @PutMapping: Utilizando para "atualizar" todos os dados na API
// @DeleteMapping: Utilizado para "excluir" dados na API
// @PatchMapping: Utilizado para "atualizar parcialmente" dados na API, exemplo mudar o status de um produto 

// ResponseEntity: Controla a resposta HTTP completa de uma API, permitindo definir o corpo (body), o código de status (200, 201, 400 etc)
//                 e os cabeçalhos (headers)


@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    // Ligando meu controlador com o respectivo serviço
    @Autowired
    private ProdutoServices produtoServices;

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        return ResponseEntity.ok().body(produtoServices.listarTodos());
    }

    // Pesquisar produto por ID
    // Ultilize o "?" ou "object" quando o retorno  pode ser objetos diferentes (produto responseentity)

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarProdutoPorId(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            Produto produto = produtoServices.buscarPorId(idLong);

            if (produto == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produto com o id " + id + " não encontrado.");
            }

            return ResponseEntity.ok(produto);

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Id " + id + " inválido, utilize um valor numérico.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> salvarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoServices.salvar(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Excluir o produto por ID

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirProdutoPorId(@PathVariable String id) {
        try {
            Long idLong = Long.parseLong(id);
            Produto produtoBanco = produtoServices.buscarPorId(idLong);

            if (produtoBanco == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produto com o id " + id + " não encontrado.");
            }

            boolean excluido = produtoServices.excluir(idLong);

            if (excluido) {
                return ResponseEntity.ok("Produto com o id " + id + " excluído com sucesso.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao excluir o produto com o id " + id);
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Id " + id + " inválido, utilize um valor numérico.");
        }
    }

    // Atualizar produto

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarProduto(@PathVariable String id, @RequestBody Produto produtoAtualizado) {
        try {
            Long idLong = Long.parseLong(id);
            Produto produtoBanco = produtoServices.buscarPorId(idLong);

            if (produtoBanco == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Produto com o id " + id + " não encontrado.");
            }

            Produto produtoAtualizadoBanco = produtoServices.atualizar(idLong, produtoAtualizado);

            if (produtoAtualizadoBanco != null) {
                return ResponseEntity.ok(produtoAtualizadoBanco);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao atualizar o produto com o id " + id);
            }

        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Id " + id + " inválido, utilize um valor numérico.");
        }
    }
}