/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.project.aigrocery;

 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import java.util.Scanner;



import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;

import com.project.aigrocery.AI.Recomendation;
import com.project.aigrocery.modelsDAO.Banco;
 
@SpringBootApplication
public class AIGrocery {

   @Autowired
   public static Banco banco;

   public static void main(String[] args)  {
      banco = new Banco();
        
        //testeApresentacao();
   }
}