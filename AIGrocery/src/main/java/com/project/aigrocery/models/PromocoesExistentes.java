package com.project.aigrocery.models;

import java.util.ArrayList;

public class PromocoesExistentes {
    private ArrayList<Promocao> promocoes;

    public PromocoesExistentes(){
        this.promocoes = new ArrayList<>();
    }

    public PromocoesExistentes(ArrayList<Promocao> promocoes){
        this.promocoes = new ArrayList<>(promocoes);
    }

    public PromocoesExistentes(PromocoesExistentes outro){
        this.promocoes = new ArrayList<>(outro.promocoes);
    }

    public void addPromocao(Promocao promocao){
        this.promocoes.add(promocao);
    }

    public ArrayList<Promocao> get_promocoes (){
        return this.promocoes;
    }
}
