package com.project.aigrocery;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
    private static Connection connection;
    public static Connection ConnectBD() throws ClassNotFoundException, SQLException {

        DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:singlestore://svc-3482219c-a389-4079-b18b-d50662524e8a-shared-dml.aws-virginia-6.svc.singlestore.com:3333/db_ctrlaltf_afa20?useSSL=true")
            .username("ctrlaltf4-bdf2c")
            .password("MFOzVahmFDA07igFYpjj8yqmiSmybUV2")
            .driverClassName("com.singlestore.jdbc.Driver")
            .build();

            
            try{
                connection = dataSource.getConnection();
                System.out.println("Conectado com sucesso ao banco!");
            } catch (Exception e) {
                e.printStackTrace();
            }
          
          return connection;
    }
}

