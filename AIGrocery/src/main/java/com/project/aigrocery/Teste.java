package com.project.aigrocery;

import java.util.Scanner;
import com.project.aigrocery.AI.Recomendation;
import com.project.aigrocery.modelsDAO.Banco;

public class Teste {

    public static void teste() 
    {
        
    Banco banco = new Banco();
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
      String text1 = banco.users.get(input).toString();
      String text2 = banco.users.get(input).getHistorico_90_dias().toString();
      System.out.println("User: \n" + text1);
      System.out.println("Historico 90 dias: \n" + text2);
      System.out.println("----------------------------------------------------------------------------------------------");

      System.out.println("TOP 5 Itens do utilizador: \n");
      System.out.println(Recomendation.recomendarTrend(banco.users.get(input).getNome(), banco.users.get(input).getHistorico_90_dias()));
      System.out.println("----------------------------------------------------------------------------------------------");
      scanner.close();
    }
}
