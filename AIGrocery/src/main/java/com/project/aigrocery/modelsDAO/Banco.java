package com.project.aigrocery.modelsDAO;

import com.project.aigrocery.DataBaseConfig;
import com.project.aigrocery.AI.Recomendation;
import com.project.aigrocery.models.*;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class Banco {
    public ArrayList<Produto> produtos = new ArrayList<>();
    public ArrayList<Promocao> promocoes = new ArrayList<>();
    public ArrayList<Historico90Dias> historico90Dias = new ArrayList<>();
    public ArrayList<Users> users = new ArrayList<>();

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
        inicia();
        teste();
    }


    public void teste() 
    {
      Scanner scanner = new Scanner(System.in);
   
      
      System.out.println("----------------------------------------------------------------------------------------------");
      
      System.out.println("Qual pessoa pretende analisar: ");
      System.out.println("0 - Healthy User ");
      System.out.println("1 - Economic Family ");
      System.out.println("2 - Gourmet Family ");
      System.out.println("3 - Lazy Family ");
      System.out.println("Input: ");
      int input = scanner.nextInt();
      
      System.out.println("----------------------------------------------------------------------------------------------");
      String text1 = users.get(input).toString();
      String text2 = users.get(input).getHistorico_90_dias().toString();
      System.out.println("User: \n" + text1);
      System.out.println("Historico 90 dias: \n" + text2);
      System.out.println("----------------------------------------------------------------------------------------------");

      System.out.println("TOP 5 Itens do utilizador: \n");
      System.out.println(Recomendation.recomendarTrend(users.get(input).getNome(), users.get(input).getHistorico_90_dias()));
      System.out.println("----------------------------------------------------------------------------------------------");
      scanner.close();
    }



    public void inicia(){

    
        iniciaProdutos();
        iniciaPromocoes();

        // Initialize historico90Dias with four empty Historico90Dias objects
        for (int i = 0; i < 4; i++) {
            historico90Dias.add(new Historico90Dias());
        }

        iniciaHistorico90Dias();

        // 4 utilizadores
        Users user1 = new Users(0, "Alice", "Healthy User", historico90Dias.get(0));
        Users user2 = new Users(1, "Bob", "Economic Family", historico90Dias.get(1));
        Users user3 = new Users(2, "Charlie", "Gourmet Family", historico90Dias.get(2));
        Users user4 = new Users(3, "Diana", "Lazy Family", historico90Dias.get(3));

        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        
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
                var resultSet = statement.executeQuery("SELECT * FROM HistoricoCompras");

                ArrayList<Produto> produtosHistorico0 = new ArrayList<>();
                ArrayList<LocalDate> datasHistorico0 = new ArrayList<>();
                ArrayList<Produto> produtosHistorico1 = new ArrayList<>();
                ArrayList<LocalDate> datasHistorico1 = new ArrayList<>();
                ArrayList<Produto> produtosHistorico2 = new ArrayList<>();
                ArrayList<LocalDate> datasHistorico2 = new ArrayList<>();
                ArrayList<Produto> produtosHistorico3 = new ArrayList<>();
                ArrayList<LocalDate> datasHistorico3 = new ArrayList<>();
                
                LocalDate dataAtual = LocalDate.now();
                while (resultSet.next()) {
                    if (dataAtual.minusDays(90).isAfter(resultSet.getDate("data_compra").toLocalDate())) {
                        continue; // Ignora compras mais antigas que 90 dias
                    }

                    for(Produto p : produtos) {
                        if (p.get_id_prod().equals(resultSet.getString("id_prod"))) {
                            int num = resultSet.getInt("id");

                            if(num == 0)
                            {
                                produtosHistorico0.add(p);
                                datasHistorico0.add(resultSet.getDate("data_compra").toLocalDate());
                                break;
                            }
                            if(num == 1)
                            {
                                produtosHistorico1.add(p);
                                datasHistorico1.add(resultSet.getDate("data_compra").toLocalDate());
                                break;
                            }
                            if(num == 2)
                            {
                                produtosHistorico2.add(p);
                                datasHistorico2.add(resultSet.getDate("data_compra").toLocalDate());
                                break;
                            }
                            if(num == 3)
                            {
                                produtosHistorico3.add(p);
                                datasHistorico3.add(resultSet.getDate("data_compra").toLocalDate());
                                break;
                            }
                            
                        }
                    }
                }

                historico90Dias.get(0).adicionarCompraArray(produtosHistorico0, datasHistorico0);
                historico90Dias.get(1).adicionarCompraArray(produtosHistorico1, datasHistorico1);
                historico90Dias.get(2).adicionarCompraArray(produtosHistorico2, datasHistorico2);
                historico90Dias.get(3).adicionarCompraArray(produtosHistorico3, datasHistorico3);
                System.out.println("Histórico de 90 dias carregado.");
                
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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public ArrayList<Promocao> getPromocoes() {
        return promocoes;
    }

    public void setPromocoes(ArrayList<Promocao> promocoes) {
        this.promocoes = promocoes;
    }

    public ArrayList<Historico90Dias> getHistorico90Dias() {
        return historico90Dias;
    }

    public void setHistorico90Dias(ArrayList<Historico90Dias> historico90Dias) {
        this.historico90Dias = historico90Dias;
    }

    public Connection getConexao() {
        return conexao;
    }

    public void setConexao(Connection conexao) {
        this.conexao = conexao;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
}
