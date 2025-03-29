package com.project.aigrocery.AI;

import com.project.aigrocery.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recomendation {

    public static void main(String[] args) {
        // Criar alguns produtos fictícios
        List<LocalDate> datasCompra = new ArrayList<>();
        datasCompra.add(LocalDate.now().minusDays(10)); // Compra há 10 dias
        datasCompra.add(LocalDate.now().minusDays(10)); // Compra há 10 dias
        datasCompra.add(LocalDate.now().minusDays(20)); // Compra há 20 dias
        datasCompra.add(LocalDate.now().minusDays(30)); // Compra há 30 dias
        datasCompra.add(LocalDate.now().minusDays(30)); // Compra há 30 dias
        datasCompra.add(LocalDate.now().minusDays(40)); // Compra há 40 dias
        datasCompra.add(LocalDate.now().minusDays(40)); // Compra há 40 dias
        datasCompra.add(LocalDate.now().minusDays(40)); // Compra há 40 dias
        datasCompra.add(LocalDate.now().minusDays(50)); // Compra há 50 dias
        datasCompra.add(LocalDate.now().minusDays(60)); // Compra há 60 dias
        datasCompra.add(LocalDate.now().minusDays(60)); // Compra há 60 dias
        datasCompra.add(LocalDate.now().minusDays(70)); // Compra há 70 dias
        datasCompra.add(LocalDate.now().minusDays(80)); // Compra há 80 dias
        datasCompra.add(LocalDate.now().minusDays(80)); // Compra há 80 dias
        datasCompra.add(LocalDate.now().minusDays(90)); // Compra há 90 dias
        datasCompra.add(LocalDate.now().minusDays(90)); // Compra há 90 dias


        Produto p1 = new Produto(Categoria.GERAL, "Arroz", "MarcaA", "001", 5.99, 100, 3, 2, 4, 5, 3);
        Produto p2 = new Produto(Categoria.GERAL, "Suco", "MarcaB", "002", 3.49, 50, 2, 3, 3, 4, 4);
        Produto p3 = new Produto(Categoria.GERAL, "Detergente", "MarcaC", "003", 2.99, 30, 1, 1, 2, 3, 5);
        Produto p4 = new Produto(Categoria.GERAL, "Sabonete", "MarcaD", "004", 1.99, 20, 2, 2, 3, 4, 3);
        Produto p5 = new Produto(Categoria.GERAL, "Feijão", "MarcaE", "005", 4.49, 80, 4, 3, 5, 5, 4);

        // Criar histórico de compras com produtos fictícios
        List<Produto> produtosComprados = Arrays.asList(p1, p2, p1, p3, p2, p1, p4, p5, p3, p2, p5, p5, p4, p2, p1, p3);
        
        Historico90Dias historico = new Historico90Dias(produtosComprados, datasCompra);
        
        // Criar uma instância da classe de recomendação
        Recomendation recomendation = new Recomendation();
        
        // Obter produtos em alta
        List<String> produtosEmAlta = produtosEmAlta(3, historico);
        System.out.println("Produtos em alta: " + produtosEmAlta);
        
        // Testar recomendação de tendência para um cliente fictício
        List<String> recomendacoes = recomendarTrend("ClienteTeste", historico);
        System.out.println("Recomendações para ClienteTeste: " + recomendacoes);
        
        // Verificar necessidade de promoção
        boolean precisaDePromocao = verificarPromocao(historico);
        if (precisaDePromocao) {
            System.out.println("O cliente pode precisar de uma promoção para incentivar compras.");
        } else {
            System.out.println("O cliente mantém um bom ritmo de compras.");
        }
        
        // Verificar produtos que precisam de promoção
        List<String> produtosParaPromocao = recomendation.produtosNecessitamPromocao(historico);
        System.out.println("Produtos que necessitam de promoção: " + produtosParaPromocao);
    }
    
    public static boolean verificarPromocao(Historico90Dias historico) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioUltimos30 = hoje.minusDays(30);
        LocalDate inicioPeriodoAnterior = hoje.minusDays(90);
        LocalDate fimPeriodoAnterior = hoje.minusDays(31);
        
        long comprasUltimos30 = historico.getDatasCompra().stream()
            .filter(data -> data.isAfter(inicioUltimos30)).count();
        
        long compras60Anteriores = historico.getDatasCompra().stream()
            .filter(data -> data.isAfter(inicioPeriodoAnterior) && data.isBefore(fimPeriodoAnterior)).count();
        
        return comprasUltimos30 < compras60Anteriores;
    }
    
    public List<String> produtosNecessitamPromocao(Historico90Dias historico) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioUltimos30 = hoje.minusDays(30);
        LocalDate inicioPeriodoAnterior = hoje.minusDays(90);
        LocalDate fimPeriodoAnterior = hoje.minusDays(31);

        Map<String, Long> contagemUltimos30 = new HashMap<>();
        Map<String, Long> contagem60Anteriores = new HashMap<>();

        for (int i = 0; i < historico.getProdutos().size(); i++) {
            Produto produto = historico.getProdutos().get(i);
            LocalDate dataCompra = historico.getDatasCompra().get(i);
            
            if (dataCompra.isAfter(inicioUltimos30)) {
                contagemUltimos30.put(produto.get_nome(), contagemUltimos30.getOrDefault(produto.get_nome(), 0L) + 1);
            } else if (dataCompra.isAfter(inicioPeriodoAnterior) && dataCompra.isBefore(fimPeriodoAnterior)) {
                contagem60Anteriores.put(produto.get_nome(), contagem60Anteriores.getOrDefault(produto.get_nome(), 0L) + 1);
            }
        }

        List<String> produtosParaPromocao = new ArrayList<>();
        for (String produto : contagem60Anteriores.keySet()) {
            long comprasAntes = contagem60Anteriores.getOrDefault(produto, 0L);
            long comprasAgora = contagemUltimos30.getOrDefault(produto, 0L);
            
            if (comprasAgora < comprasAntes) {
                produtosParaPromocao.add(produto);
            }
        }
        
        return produtosParaPromocao;
    }


    //parado
    public void padraoDeCompra() {
        List<Produto> produtos = new ArrayList<>();
        List<LocalDate> datasCompra = new ArrayList<>();

        // Using the constructor with arguments
        Historico90Dias historico = new Historico90Dias(produtos, datasCompra);

        Date dataAtual = new Date();
        System.out.println("Data atual: " + dataAtual);
    }



    public static List<String> produtosEmAlta(int limite, Historico90Dias historico90dias) {
        Map<String, Integer> contagemProdutos = new HashMap<>();
        // Conta quantas vezes cada produto foi comprado
        for (Produto produto : historico90dias.getProdutos()) {
            contagemProdutos.put(produto.get_nome(), contagemProdutos.getOrDefault(produto.get_nome(), 0) + 1);
        }

        // Ordena produtos mais comprados primeiro
        List<Map.Entry<String, Integer>> produtosOrdenados = new ArrayList<>(contagemProdutos.entrySet());
        produtosOrdenados.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        // Retorna os 'limite' produtos mais comprados
        List<String> tendencias = new ArrayList<>();
        for (int i = 0; i < Math.min(limite, produtosOrdenados.size()); i++) {
            tendencias.add(produtosOrdenados.get(i).getKey());
        }
        return tendencias;
    }

    public static List<String> recomendarTrend(String cliente, Historico90Dias historicoCompras) {

        List<Produto> comprasClientes = historicoCompras.getProdutos();
        if (comprasClientes == null) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }

        // Identificar tendências da comunidade
        List<String> trend = produtosEmAlta(3, historicoCompras);

        // Filtra apenas produtos que o cliente ainda não comprou
        List<String> recomendacoes = new ArrayList<>(trend);
        return recomendacoes;
    }
}
