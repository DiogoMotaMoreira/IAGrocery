/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.project.aigrocery;

 import org.springframework.boot.CommandLineRunner;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;
 
 import com.project.aigrocery.modelsDAO.Banco;
 
@SpringBootApplication
@Configuration
public class AIGrocery {

   @Autowired
   private Banco banco;

   public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Banco banco = new Banco();
        banco.inicia();
        
   }
}