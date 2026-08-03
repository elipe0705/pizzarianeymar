package com.itb.inf2fm.pizzarianeymar.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itb.inf2fm.pizzarianeymar.model.entity.Produto;
import com.itb.inf2fm.pizzarianeymar.model.services.ProdutoService;

/**
 * Camada Controller do Produto.
 * Recebe as requisicoes HTTP, chama o Service e devolve a resposta.
 *
 * @RestController  -> diz ao Spring que esta classe e um controller REST
 *                     (todo retorno de metodo ja vira JSON no corpo da resposta).
 * @RequestMapping  -> define a URL base de todos os metodos desta classe.
 */
@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    /**
     * @Autowired -> o Spring injeta automaticamente uma instancia de ProdutoService.
     * Nao precisamos dar "new ProdutoService()".
     */
    private final ProdutoService produtoService;

    ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // ==================== CREATE ====================

    /**
     * Cadastra um novo produto.
     *
     * @PostMapping  -> responde ao metodo HTTP POST em /produtos
     * @RequestBody  -> converte o JSON enviado pelo cliente em um objeto Produto
     *
     * Retorno: 201 Created + o produto salvo (ja com o id gerado) e o
     * cabecalho Location apontando para /api/v1/produtos/{id}.
     */
    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {

        Produto produtoSalvo = produtoService.salvar(produto);

        URI localizacao = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produtoSalvo.getId1())
                .toUri();

        return ResponseEntity.created(localizacao).body(produtoSalvo);
    }

    // ==================== READ ====================

    /**
     * Lista todos os produtos cadastrados.
     *
     * @GetMapping -> responde ao metodo HTTP GET em /produtos
     *
     * Retorno: 200 OK + a lista de produtos.
     */
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {

        List<Produto> produtos = produtoService.listarTodos();

        return ResponseEntity.ok(produtos);
    }

    /**
     * Busca um produto pelo id.
     *
     * @GetMapping("/{id}") -> responde ao GET em /produtos/1, /produtos/2 ...
     * @PathVariable        -> pega o valor que veio na URL e joga no parametro "id"
     *
     * Retorno: 200 OK + produto, ou 404 Not Found se nao existir.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {

        Produto produto = produtoService.buscarPorId(id);

        // Produto nao encontrado na lista
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produto);
    }

    // ==================== UPDATE ====================

    /**
     * Atualiza os dados de um produto ja existente.
     *
     * @PutMapping("/{id}") -> responde ao metodo HTTP PUT em /produtos/{id}
     * @PathVariable        -> id do produto que sera alterado
     * @RequestBody         -> novos dados do produto (em JSON)
     *
     * Retorno: 200 OK + produto atualizado, ou 404 Not Found se nao existir.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {

        Produto produtoAtualizado = produtoService.atualizar(id, produto);

        if (produtoAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produtoAtualizado);
    }

    // ==================== DELETE ====================

    /**
     * Exclui um produto pelo id.
     *
     * @DeleteMapping("/{id}") -> responde ao metodo HTTP DELETE em /produtos/{id}
     * @PathVariable           -> id do produto que sera excluido
     *
     * Retorno: 204 No Content quando excluir, ou 404 Not Found se nao existir.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {

        boolean excluido = produtoService.excluir(id);

        if (!excluido) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
