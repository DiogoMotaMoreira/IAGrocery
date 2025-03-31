package com.project.aigrocery.AI;

import com.project.aigrocery.models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Recomendation {

    /*public static void main(String[] args) throws IOException {
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


        Produto p1 = new Produto(Categoria.GRAOS_E_CEREAIS, "Arroz", "MarcaA", "001", 5.99, 100, 3, 2, 4, 5, 3);
        Produto p2 = new Produto(Categoria.BEBIDAS, "Coca-Cola", "MarcaB", "002", 3.49, 50, 2, 3, 3, 4, 4);
        Produto p3 = new Produto(Categoria.TEMPEROS_E_CONDIMENTOS, "Piri-piri", "MarcaC", "003", 2.99, 30, 1, 1, 2, 3, 5);
        Produto p4 = new Produto(Categoria.DOCES_E_SOBREMESAS, "Chocolate", "MarcaD", "004", 1.99, 20, 2, 2, 3, 4, 3);
        Produto p5 = new Produto(Categoria.CONGELADOS, "Douradinhos", "MarcaE", "005", 4.49, 80, 4, 3, 5, 5, 4);

        // Criar histórico de compras com produtos fictícios
        List<Produto> produtosComprados = Arrays.asList(p1, p2, p1, p3, p2, p1, p4, p5, p3, p2, p5, p5, p4, p2, p1, p3);
        
        Historico90Dias historico = new Historico90Dias(0,produtosComprados, datasCompra);
        

        // Criar promoções
        Promocao promocao1 = new Promocao(
            Categoria.GRAOS_E_CEREAIS, "Arroz", "MarcaA", "001", 4.99, 100, 3, 2, 4, 5, 3,
            "Desconto de 20% no Arroz MarcaA", "2023-12-31"
        );
        Promocao promocao2 = new Promocao(
            Categoria.BEBIDAS, "IceTea", "MarcaB", "002", 2.99, 50, 2, 3, 3, 4, 4,
            "Desconto de 15% no Suco MarcaB", "2023-12-31"
        );
        Promocao promocao3 = new Promocao(
            Categoria.TEMPEROS_E_CONDIMENTOS, "Oregãos", "MarcaC", "003", 2.49, 30, 1, 1, 2, 3, 5,
            "Desconto de 10% no Detergente MarcaC", "2023-12-31"
        );
        Promocao promocao4 = new Promocao(
            Categoria.GRAOS_E_CEREAIS, "Espaguete", "MarcaF", "006", 3.49, 60, 3, 2, 4, 4, 3,
            "Desconto de 25% no Espaguete MarcaF", "2023-12-31"
        );

        // Adicionar promoções a uma lista
        ArrayList<Promocao> listaPromocoes = new ArrayList<>();
        listaPromocoes.add(promocao1);
        listaPromocoes.add(promocao2);
        listaPromocoes.add(promocao3);
        listaPromocoes.add(promocao4);

        // Criar objeto PromocoesExistentes e adicionar à lista
        PromocoesExistentes promocoesList = new PromocoesExistentes(listaPromocoes);




        // Criar uma instância da classe de recomendação
        Recomendation recomendation = new Recomendation();
        
        // Obter produtos em alta
        List<String> produtosEmAlta = produtosEmAlta(3, historico);
        System.out.println("Produtos em alta: " + produtosEmAlta);
        
        // Testar recomendação de tendência para um cliente fictício
        List<String> recomendacoes = recomendarTrend("ClienteTeste", historico);
        System.out.println("Recomendações para ClienteTeste: " + recomendacoes);
        




        
        // Verificar produtos que precisam de promoção
        //indica quais produtos foi reduzida a compra
        List<Produto> produtosParaPromocao = Recomendation.produtosNecessitamPromocao(historico);
        for(Produto produto : produtosParaPromocao) {
            System.out.println("Produto que necessita de promoção: " + produto.get_nome());
        }

        //opções:
        //1. veriifcar itens associados que tenham promoção
        List<String> produtosComPromocao = Recomendation.produtosSimilaresComPromocao(produtosParaPromocao, promocoesList);
        for(String produto : produtosComPromocao) {
            System.out.println("Produto com promoção similares: " + produto);
        }
    }*/

    
    public static List<String> produtosSimilaresComPromocao(List<Produto> produtos, PromocoesExistentes promocoes) throws IOException {
        List<String> produtosComPromocao = new ArrayList<>();

        for (Produto produto : produtos) {
            double[] embeddingProduto = OpenAIEmbeddings.getEmbedding(produto.toStringJ());

                for (Promocao promocao : promocoes.get_promocoes()) {
                        if (!produto.getClass().equals(promocao.getClass())) {
                            break;
                        }
                        double[] embeddingPromocao = OpenAIEmbeddings.getEmbedding(promocao.toStringJ());
                        double similaridade = OpenAIEmbeddings.cosineSimilarity(embeddingProduto, embeddingPromocao);

                        // Considera produtos similares se a similaridade for maior que um limiar (ex: 0.8)
                        System.out.println("Similaridade: " + similaridade);
                        if (similaridade > 0.75) {
                            produtosComPromocao.add(promocao.get_nome());
                        }
                }
            
        }

        return produtosComPromocao;
    }

    
    public static boolean verificarPromocao(Historico90Dias historico) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioUltimos30 = hoje.minusDays(30);
        LocalDate inicioSegundo30 = hoje.minusDays(60);
        LocalDate fimSegundo30 = hoje.minusDays(31);
        LocalDate inicioTerceiro30 = hoje.minusDays(90);
        LocalDate fimTerceiro30 = hoje.minusDays(61);
    
        // Contagem de compras nos 30 primeiros dias
        long comprasPrimeiros30 = historico.getDatasCompra().stream()
            .filter(data -> data.isAfter(inicioUltimos30) && data.isBefore(hoje)).count();
    
        // Contagem de compras no segundo e terceiro período de 30 dias
        long comprasSegundo30 = historico.getDatasCompra().stream()
            .filter(data -> data.isAfter(inicioSegundo30) && data.isBefore(fimSegundo30)).count();
    
        long comprasTerceiro30 = historico.getDatasCompra().stream()
            .filter(data -> data.isAfter(inicioTerceiro30) && data.isBefore(fimTerceiro30)).count();
    
        // Média dos últimos 60 dias
        long mediaCompras60 = (comprasSegundo30 + comprasTerceiro30) / 2;
    
        // Se as compras nos primeiros 30 dias forem menores que a média dos 60 dias anteriores, sugere promoção
        return comprasPrimeiros30 < mediaCompras60;
    }

    
    
    public static List<Produto> produtosNecessitamPromocao(Historico90Dias historico) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioUltimos30 = hoje.minusDays(30);
        LocalDate inicioPeriodoAnterior = hoje.minusDays(90);
        LocalDate fimPeriodoAnterior = hoje.minusDays(31);

        Map<String, Long> contagemUltimos30 = new HashMap<>();
        Map<String, Long> contagemPeriodoAnterior = new HashMap<>();

        // Contagem de compras nos últimos 30 dias
        for (int i = 0; i < historico.getProdutos().size(); i++) {
            Produto produto = historico.getProdutos().get(i);
            LocalDate dataCompra = historico.getDatasCompra().get(i);

            if (dataCompra.isAfter(inicioUltimos30) || dataCompra.isEqual(inicioUltimos30)) {
                contagemUltimos30.put(produto.get_nome(), contagemUltimos30.getOrDefault(produto.get_nome(), 0L) + 1);
            }
        }

        // Contagem de compras nos 60 dias anteriores
        for (int i = 0; i < historico.getProdutos().size(); i++) {
            Produto produto = historico.getProdutos().get(i);
            LocalDate dataCompra = historico.getDatasCompra().get(i);

            if ((dataCompra.isAfter(inicioPeriodoAnterior) || dataCompra.isEqual(inicioPeriodoAnterior)) 
                && dataCompra.isBefore(fimPeriodoAnterior)) {
            contagemPeriodoAnterior.put(produto.get_nome(), contagemPeriodoAnterior.getOrDefault(produto.get_nome(), 0L) + 1);
            }
        }

        List<Produto> produtosParaPromocao = new ArrayList<>();
        
        for (String produtoNome : contagemPeriodoAnterior.keySet()) {
            long comprasAntes = contagemPeriodoAnterior.getOrDefault(produtoNome, 0L);
            long comprasAgora = contagemUltimos30.getOrDefault(produtoNome, 0L);
        
            // Add to promotion list only if purchases in the last 30 days are significantly lower than before
            if (comprasAgora < comprasAntes / 2 && comprasAntes > 0) {
                System.out.println("Produto: " + produtoNome);
                System.out.println("1: " + comprasAgora);
                System.out.println("2: " + comprasAntes);
                System.out.println("--------------------");
                
                
                // Retrieve the Produto object by its name
                for (Produto produto : historico.getProdutos()) {
                    if (produto.get_nome().equals(produtoNome)) {
                        produtosParaPromocao.add(produto);
                        break;
                    }
                }
            }
        }

        return produtosParaPromocao;
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
