package com.project.aigrocery.AI;

import com.project.aigrocery.models.Historico90Dias;
import java.util.Date;

public class Recomendation {

    public void padraoDeCompra()
    {
        Historico90Dias historico = new Historico90Dias(); // tem que ser um getter da base de dados (DAO)

        Date dataAtual = new Date();
        System.out.println("Data atual: " + dataAtual);
    }
}
