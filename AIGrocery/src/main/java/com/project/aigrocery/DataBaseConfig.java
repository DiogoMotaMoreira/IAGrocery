package com.project.aigrocery;

import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DataBaseConfig {
    private static Connection connection;

    public static Connection ConnectBD() throws ClassNotFoundException, SQLException {
        // Load the driver class
        Class.forName("com.singlestore.jdbc.Driver");

        // Retrieve database connection properties from system properties
        String url = System.getProperty("db.url", "jdbc:singlestore://svc-3482219c-a389-4079-b18b-d50662524e8a-shared-dml.aws-virginia-6.svc.singlestore.com:3333/db_ctrlaltf_afa20?useSSL=true&sslrootcert=C:\\Users\\diogo\\OneDrive\\Documentos\\GitHub\\IAGrocery\\AIGrocery\\src\\main\\java\\com\\project\\aigrocery\\singlestore_bundle.pem");
        String username = System.getProperty("db.username", "ctrlaltf4-bdf2c");
        String password = System.getProperty("db.password", "MFOzVahmFDA07igFYpjj8yqmiSmybUV2");

        // Establish the connection
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Conectado com sucesso ao banco!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }

        return connection;
    }
}

