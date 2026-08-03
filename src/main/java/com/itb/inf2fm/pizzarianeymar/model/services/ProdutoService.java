package com.itb.inf2fm.pizzarianeymar.model.services;

import com.itb.inf2fm.pizzarianeymar.model.entity.Produto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Camada Service do Produto.
 * Guarda os produtos em memoria (ainda sem banco de dados).
 */
@Service
public class ProdutoService {

    private final List<Produto> produtos = Collections.synchronizedList(new ArrayList<>());

    /** Gerador do proximo id. Comeca no 2 porque ja existem 2 produtos de exemplo. */
    private final AtomicLong sequencia = new AtomicLong(2);

    public ProdutoService() {
        produtos.add(criarProduto(1L, "Pizza Calabresa", "Calabresa, cebola e molho de tomate",
                new BigDecimal("45.90")));
        produtos.add(criarProduto(2L, "Pizza Portuguesa", "Presunto, ovo, cebola e azeitona",
                new BigDecimal("52.90")));
    }

    // CREATE
    public Produto salvar(Produto produto) {

        // o id sempre e gerado aqui, nunca vem do cliente
        produto.setId(sequencia.incrementAndGet());

        produtos.add(produto);

        return produto;
    }

    // READ - listar todos
    public List<Produto> listarTodos() {
        // copia defensiva: quem chamar nao consegue alterar a lista interna
        return new ArrayList<>(produtos);
    }

    // READ - buscar por id
    public Produto buscarPorId(Long id) {

        if (id == null) {
            return null;
        }

        for (Produto produto : produtos) {

            if (id.equals(produto.getId1())) {
                return produto;
            }
        }

        return null;
    }

    // UPDATE
    public Produto atualizar(Long id, Produto produtoAtualizado) {

        Produto produto = buscarPorId(id);

        if (produto == null) {
            return null;
        }

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setValorVenda1(produtoAtualizado.getValorVenda());
        produto.setCodStatus(produtoAtualizado.isCodStatus());

        return produto;
    }

    // DELETE
    public boolean excluir(Long id) {

        Produto produto = buscarPorId(id);

        if (produto == null) {
            return false;
        }

        produtos.remove(produto);

        return true;
    }

    private Produto criarProduto(Long id, String nome, String descricao, BigDecimal valorVenda) {

        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setValorVenda1(valorVenda);
        produto.setCodStatus(true);

        return produto;
    }
}
