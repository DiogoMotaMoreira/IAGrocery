package com.project.aigrocery.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Historico90Dias {
    private ArrayList<Produto> produtos;
    private ArrayList<LocalDate> datasCompra;

    public Historico90Dias() {
        this.produtos = new ArrayList<>();
        this.datasCompra = new ArrayList<>();
    }

    // Construtor com argumentos (recebe listas de produtos e datas)
    public Historico90Dias(List<Produto> produtos, List<LocalDate> datasCompra) {
        this.produtos = new ArrayList<>(produtos);
        this.datasCompra = new ArrayList<>(datasCompra);
    }

    // Construtor de cópia (clona um objeto Historico90Dias)
    public Historico90Dias(Historico90Dias outro) {
        this.produtos = new ArrayList<>(outro.produtos);
        this.datasCompra = new ArrayList<>(outro.datasCompra);
    }

    // Adiciona uma compra ao histórico, garantindo que a data esteja nos últimos 90 dias
    public void adicionarCompra(Produto produto, LocalDate data) {
        LocalDate limite = LocalDate.now().minusDays(90);
        if (!data.isBefore(limite)) { // Só adiciona se a data for dentro dos últimos 90 dias
            this.produtos.add(produto);
            this.datasCompra.add(data);
        }
    }

    public Historico90Dias getHistoricoUltimos30Dias() { // Retorna o histórico dos últimos 30 dias
        Historico90Dias historico30Dias = new Historico90Dias();
        LocalDate limite = LocalDate.now().minusDays(30);

        for (int i = 0; i < this.datasCompra.size(); i++) {
            if (!this.datasCompra.get(i).isBefore(limite)) { // Se a data for dentro dos últimos 30 dias
                historico30Dias.adicionarCompra(this.produtos.get(i), this.datasCompra.get(i));
            }
        }

        return historico30Dias;
    }

    // Retorna a lista de datas de compra para um produto específico
    public List<LocalDate> getDatasCompraPorProduto(Produto p) {
        List<LocalDate> datas = new ArrayList<>();
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).equals(p)) {
                datas.add(datasCompra.get(i));
            }
        }
        return datas;
    }

    // Retorna a lista de produtos comprados
    public ArrayList<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    // Retorna a lista de datas das compras
    public ArrayList<LocalDate> getDatasCompra() {
        return new ArrayList<>(datasCompra);
    }

    // Remove compras que tenham mais de 90 dias
    public void limparHistoricoAntigo() {
        LocalDate limite = LocalDate.now().minusDays(90);
        Iterator<LocalDate> dataIterator = datasCompra.iterator();
        Iterator<Produto> produtoIterator = produtos.iterator();

        while (dataIterator.hasNext() && produtoIterator.hasNext()) {
            LocalDate data = dataIterator.next();
            produtoIterator.next();
            if (data.isBefore(limite)) {
                dataIterator.remove();
                produtoIterator.remove();
            }
        }
    }

    // Exibe o histórico de compras formatado
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Histórico de Compras (últimos 90 dias):\n");
        for (int i = 0; i < produtos.size(); i++) {
            sb.append(datasCompra.get(i)).append(" - ").append(produtos.get(i).get_nome()).append("\n");
        }
        return sb.toString();
    }

    // Método equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Historico90Dias that = (Historico90Dias) obj;
        return Objects.equals(produtos, that.produtos) &&
               Objects.equals(datasCompra, that.datasCompra);
    }
}
