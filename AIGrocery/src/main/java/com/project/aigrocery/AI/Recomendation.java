package com.project.aigrocery.AI;

import com.project.aigrocery.models.Historico90Dias;
import com.project.aigrocery.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recomendation {

    public void padraoDeCompra()
    {
        
        List<Produto> produtos = new ArrayList<>(); 
        //produtos.add(new Produto(Categoria.GERAL,"Arroz", "", "", 0, 0, 0, 0, 0, 0, 0));
        //produtos.add(new Produto(Categoria.GERAL,"Feijão", "", "", 0, 0, 0, 0, 0, 0, 0));
        //produtos.add(new Produto(Categoria.GERAL,"Macarrão", "", "", 0, 0, 0, 0, 0, 0, 0));
        //produtos.add(new Produto(Categoria.GERAL,"Leite", "", "", 0, 0, 0, 0, 0, 0, 0));
        //produtos.add(new Produto(Categoria.GERAL,"Açúcar", "", "", 0, 0, 0, 0, 0, 0, 0));
        List<LocalDate> datasCompra = new ArrayList<>(); 

        // Using the constructor with arguments
        Historico90Dias historico = new Historico90Dias(produtos, datasCompra);

        Date dataAtual = new Date();
        System.out.println("Data atual: " + dataAtual);
    }
}
