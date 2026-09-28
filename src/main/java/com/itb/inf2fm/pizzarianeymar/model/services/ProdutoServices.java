package com.itb.inf2fm.pizzarianeymar.model.services;

import com.itb.inf2fm.pizzarianeymar.model.entity.Produto;
import com.itb.inf2fm.pizzarianeymar.model.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoServices {

    @Autowired // Injeção de dependência automática
    private ProdutoRepository produtoRepository;

    // Método responsável em listar todos os produtos cadastrados no banco de dados
    public List<Produto> listarTodos() {
        return produtoRepository.findAll();
    }

    // Método responsável em criar o produto no banco de dados
    public Produto salvar(Produto produto) {
        produto.setId(null); // o id é gerado pelo banco (IDENTITY), nunca vem do cliente
        produto.setCodStatus(true);
        return produtoRepository.save(produto);
    }

    // Método responsável em listar o produto por ID (retorna null se não existir)
    public Produto buscarPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    // Método responsável em atualizar o produto
    public Produto atualizar(Long id, Produto produto) {
        Produto produtoExistente = buscarPorId(id);

        if (produtoExistente == null) {
            return null;
        }

        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setTipo(produto.getTipo());
        produtoExistente.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        produtoExistente.setValorCompra(produto.getValorCompra());
        produtoExistente.setValorVenda(produto.getValorVenda());
        produtoExistente.setCodStatus(produto.isCodStatus());
        return produtoRepository.save(produtoExistente);
    }

    // Método responsável em excluir o produto (exclusão física)
    public boolean excluir(Long id) {
        Produto produtoExistente = buscarPorId(id);

        if (produtoExistente == null) {
            return false;
        }

        produtoRepository.delete(produtoExistente);
        return true;
    }
}
