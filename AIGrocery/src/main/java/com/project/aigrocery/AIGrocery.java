/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

 package com.project.aigrocery;

 import org.springframework.boot.CommandLineRunner;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;
 
 import javax.sql.DataSource;
 import java.sql.Connection;
 import java.sql.SQLException;
 
 @SpringBootApplication
 @Configuration
 public class AIGrocery implements CommandLineRunner {
     public static void main(String[] args) {
         SpringApplication.run(AIGrocery.class, args);
     }
     @Override
     public void run(String... args) throws Exception {
         try {
             DataBaseConfig.ConnectBD();
         } catch (ClassNotFoundException | SQLException e) {
             System.out.println("Error during database connection: " + e.getMessage());
         }
     }
 }