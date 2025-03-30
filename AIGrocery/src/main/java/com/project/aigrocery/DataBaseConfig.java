package com.project.aigrocery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DataBaseConfig {

    public static Connection ConnectBD() throws ClassNotFoundException, SQLException {
          // Hardcoded database credentials
          String SUser = "ctrlaltf4-bdf2c";
          String SPassword = "55JKamUdC02xWx4SsdEwZ5N87peP2rwK";
          String SUrl = "jdbc:singlestore://svc-3482219c-a389-4079-b18b-d50662524e8a-shared-dml.aws-virginia-6.svc.singlestore.com:3333/db_ctrlaltf_afa20?user=ctrlaltf4-bdf2c&password=55JKamUdC02xWx4SsdEwZ5N87peP2rwK&useSSL=true&trustCertificateKeyStoreUrl=https://portal.singlestore.com/static/ca/singlestore_bundle.pem";

          System.out.println("SUser: " + SUser);
          System.out.println("SPassword: " + SPassword);
          System.out.println("SUrl: " + SUrl);

          Class.forName("com.singlestore.jdbc.Driver");
          Connection connection = DriverManager.getConnection(SUrl);
          try {
                if (connection != null && !connection.isClosed()) {
                     System.out.println("Connection successful!");
                } else {
                     System.out.println("Failed to establish connection.");
                }
          } catch (SQLException e) {
                e.printStackTrace();
          } finally {
                if (connection != null) {
                     try {
                          connection.close();
                     } catch (SQLException e) {
                          e.printStackTrace();
                     }
                }
          }
          return connection;
    }
}
