package com.project.aigrocery.modelsDAO;

import com.project.aigrocery.DataBaseConfig;
import com.project.aigrocery.models.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Banco {
    public ArrayList<Produto> produtos = new ArrayList<>();
    public ArrayList<Promocao> promocoes = new ArrayList<>();
    public ArrayList<Historico90Dias> historico90Dias = new ArrayList<>();

    private Connection conexao;

    public Banco() {
        try {
            conexao = DataBaseConfig.ConnectBD();
            if (conexao != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao conectar ao banco de dados.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            conexao = null;
        }
    }

    public void inicia(){
        iniciaProdutos();
        iniciaPromocoes();
        iniciaHistorico90Dias();

        // 4 utilizadores
        Users user1 = new Users(1, "Alice", "Healthy User", historico90Dias.get(0));
        Users user2 = new Users(2, "Bob", "Economic Family", historico90Dias.get(1));
        Users user3 = new Users(3, "Charlie", "Gourmet Family", historico90Dias.get(2));
        Users user4 = new Users(4, "Diana", "Lazy Family", historico90Dias.get(3));

        
    }

    private void iniciaProdutos() {
        if (conexao != null) {
            try {
                System.out.println("Inicio...");
                var statement = conexao.createStatement();
                var resultSet = statement.executeQuery("SELECT * FROM produto");

                while (resultSet.next()) {
                    Produto produto = new Produto(
                    Categoria.valueOf(resultSet.getString("categoria").toUpperCase()),
                    resultSet.getString("nome"),
                    resultSet.getString("marca"),
                    resultSet.getString("id_prod"),
                    resultSet.getDouble("preco"),
                    resultSet.getInt("quant_em_arm"),
                    resultSet.getInt("tier_saudavel"),
                    resultSet.getInt("tier_dieta"),
                    resultSet.getInt("tier_familiar"),
                    resultSet.getInt("tier_marca"),
                    resultSet.getInt("tier_sustentavel")
                    );
                    
                    produtos.add(produto);
                }
                System.out.println("Fim...");

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
            e.printStackTrace();
            }
        } else {
            System.out.println("Conexão com o banco de dados não foi estabelecida.");
        }

        if (!produtos.isEmpty()) {
            System.out.println(produtos.get(0).toString());
        } else {
            System.out.println("Nenhum produto encontrado.");
        }
    }

    private void iniciaPromocoes() {
        if (conexao != null) {
            try {
                System.out.println("Iniciando promoções...");
                var statement = conexao.createStatement();
                var resultSet = statement.executeQuery("SELECT * FROM Promocao");

                while (resultSet.next()) {
                    Promocao promocao = new Promocao(
                        Categoria.valueOf(resultSet.getString("categoria").toUpperCase()),
                        resultSet.getString("nome"),
                        resultSet.getString("marca"),
                        resultSet.getString("id_prod"),
                        resultSet.getDouble("preco"),
                        resultSet.getInt("quant_em_arm"),
                        resultSet.getInt("tier_saudavel"),
                        resultSet.getInt("tier_dieta"),
                        resultSet.getInt("tier_familiar"),
                        resultSet.getInt("tier_marca"),
                        resultSet.getInt("tier_sustentavel"),
                        resultSet.getString("descricao_promocao"),
                        resultSet.getString("data_final")
                    );

                    promocoes.add(promocao);
                }
                System.out.println("Promoções carregadas.");

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Conexão com o banco de dados não foi estabelecida.");
        }

        if (!promocoes.isEmpty()) {
            System.out.println(promocoes.get(0).toString());
        } else {
            System.out.println("Nenhuma promoção encontrada.");
        }
    }

    private void iniciaHistorico90Dias() {
        if (conexao != null) {
            try {
                System.out.println("Iniciando histórico de 90 dias...");
                var statement = conexao.createStatement();
                var resultSet = statement.executeQuery("SELECT * FROM historico_90_dias");

                ArrayList<Produto> produtosHistorico = new ArrayList<>();
                ArrayList<LocalDate> datasHistorico = new ArrayList<>();

                LocalDate dataAtual = LocalDate.now();
                while (resultSet.next()) {
                    if (dataAtual.minusDays(90).isAfter(resultSet.getDate("data_compra").toLocalDate())) {
                        continue; // Ignora compras mais antigas que 90 dias
                    }

                    for(Produto p : produtos) {
                        if (p.get_id_prod().equals(resultSet.getString("id_prod"))) {
                            produtosHistorico.add(p);
                            datasHistorico.add(resultSet.getDate("data_compra").toLocalDate());
                            break;
                        }
                    }
                    
                }
                System.out.println("Histórico de 90 dias carregado.");
                historico90Dias.add(new Historico90Dias(produtosHistorico, datasHistorico));
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Conexão com o banco de dados não foi estabelecida.");
        }

        if (!historico90Dias.isEmpty()) {
            System.out.println(historico90Dias.get(0).toString());
        } else {
            System.out.println("Nenhum histórico encontrado.");
        }
    }
}
