package com.project.aigrocery.models;

import java.util.ArrayList;
import java.util.List;

public class historico {
    private List<produto> produtos;

    public historico() {
        this.produtos = new ArrayList<>();
    }

    public List<produto> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(produto p) {
        this.produtos.add(p);
    }

    public void removerProduto(produto p) {
        this.produtos.remove(p);
    }

    public void limparHistorico() {
        this.produtos.clear();
    }
}
