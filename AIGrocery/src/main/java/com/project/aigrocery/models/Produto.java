/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.aigrocery.models;

/**
 *
 * @author diogo
 */
public class Produto{
    private String nome;
    private String marca;
    private String id_prod;
    private double preco;
    private int quant_em_arm;
    private Categoria categoria;
    private int tier_saudavel; //de 1 a 5 sendo 1 menos saudavel e 5 mais saudavel
    private int tier_dieta; // 1-sem gluten 2-sem lactose 3-diabeticos 4-vegetariana 5-vegan 6-pescetariana 7-kosher 8-halal 9-geral
    private int tier_familiar; // de 1 a 5 sendo 1 itens mais individuais e 5 itens mais pack familiar
    private int tier_marca; // de 1 a 5 sendo 1 economico e 5 premium
    private int tier_sustentavel; // de 1 a 5 sendo 1 insustentavel e 5 mais sustentavel

    //construtores
    public Produto() {
        this.categoria = Categoria.GERAL;
        this.nome = "";
        this.marca = "";
        this.id_prod = "";
        this.preco = 0;
        this.quant_em_arm = 0;
        this.tier_saudavel = 0;
        this.tier_dieta = 0;
        this.tier_familiar = 0;
        this.tier_marca = 0;
        this.tier_sustentavel = 0;
    }

    public Produto(Categoria categoria, String nome, String marca, String id_prod, double preco, int quant_em_arm, int tier_saudavel, int tier_dieta, int tier_familiar, int tier_marca, int tier_sustentavel) {
        this.categoria = categoria;
        this.nome = nome;
        this.marca = marca;
        this.id_prod = id_prod;
        this.preco = preco;
        this.quant_em_arm = quant_em_arm;
        this.tier_saudavel = tier_saudavel;
        this.tier_dieta = tier_dieta;
        this.tier_familiar = tier_familiar;
        this.tier_marca = tier_marca;
        this.tier_sustentavel = tier_sustentavel;
    }

    public Produto(Produto a) {
        this.categoria = a.get_categoria();
        this.nome = a.get_nome();
        this.marca = a.get_marca();
        this.id_prod = a.get_id_prod();
        this.preco = a.get_preco();
        this.quant_em_arm = a.get_quant_arm();
        this.tier_saudavel = a.get_tier_saudavel();
        this.tier_dieta = a.get_tier_dieta();
        this.tier_familiar = a.get_tier_familiar();
        this.tier_marca = a.get_tier_marca();
        this.tier_sustentavel = a.get_tier_sustentavel();
    }



    //getters

    public Categoria get_categoria(){
        return categoria;
    }

    public String get_nome(){
        return nome;
    }

    public String get_marca(){
        return marca;
    }

    public String get_id_prod(){
        return id_prod;
    }

    public double get_preco(){
        return preco;
    }

    public int get_quant_arm(){
        return quant_em_arm;
    }

    public int get_tier_saudavel() {
        return tier_saudavel;
    }

    public int get_tier_dieta() {
        return tier_dieta;
    }

    public int get_tier_familiar() {
        return tier_familiar;
    }

    public int get_tier_marca() {
        return tier_marca;
    }

    public int get_tier_sustentavel() {
        return tier_sustentavel;
    }

    //setters

    public void set_categoria(Categoria categoria){
        this.categoria = categoria;
    }

    public void set_nome(String nome){
        this.nome = nome;
    }

    public void set_marca(String marca){
        this.marca = marca;
    }

    public void set_id_prod(String id_prod){
        this.id_prod = id_prod;
    }

    public void set_preco(double preco){
        this.preco = preco;
    }

    public void set_quant_prod(int quant_em_arm){
        this.quant_em_arm = quant_em_arm;
    }

    public void set_tier_saudavel(int tier_saudavel) {
        this.tier_saudavel = tier_saudavel;
    }

    public void set_tier_dieta(int tier_dieta) {
        this.tier_dieta = tier_dieta;
    }

    public void set_tier_familiar(int tier_familiar) {
        this.tier_familiar = tier_familiar;
    }

    public void set_tier_marca(int tier_marca) {
        this.tier_marca = tier_marca;
    }

    public void set_tier_sustentavel(int tier_sustentavel) {
        this.tier_sustentavel = tier_sustentavel;
    }

    //funcoes uteis
    
    public Produto clone(Produto a){
        return new Produto(a);
    }

    public String toString(){
        return("Produto: " + this.nome + "\n" +
                "Categoria: " + this.categoria + "\n" +
                "Marca: " + this.marca + "\n" +
                "ID: " + this.id_prod + "\n" +
                "Preço: " + this.preco + "\n" +
                "Quantidade em Armazém: " + this.quant_em_arm + "\n" +
                "Tier Saudável: " + this.tier_saudavel + "\n" +
                "Tier Dieta: " + this.tier_dieta + "\n" +
                "Tier Familiar: " + this.tier_familiar + "\n" +
                "Tier Marca: " + this.tier_marca + "\n" +
                "Tier Sustentável: " + this.tier_sustentavel)+ " ";
    }

    public String toStringJ(){
        return("Produto: " + this.nome +
                "Categoria: " + this.categoria + 
                "Marca: " + this.marca +
                "ID: " + this.id_prod +
                "Preço: " + this.preco +
                "Quantidade em Armazém: " + this.quant_em_arm +
                "Tier Saudável: " + this.tier_saudavel +
                "Tier Dieta: " + this.tier_dieta +
                "Tier Familiar: " + this.tier_familiar +
                "Tier Marca: " + this.tier_marca +
                "Tier Sustentável: " + this.tier_sustentavel)+ " ";
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if((o == null) || (this.getClass() != o.getClass())) return false;
        Produto p = (Produto) o;
        return (this.categoria.equals(p.get_categoria()) && this.nome.equals(p.get_nome()) && this.marca.equals(p.get_marca()) && 
                this.id_prod.equals(p.get_id_prod()) && this.preco == p.get_preco() && this.quant_em_arm == p.get_quant_arm() && 
                this.tier_saudavel == p.get_tier_saudavel() && this.tier_dieta == p.get_tier_dieta() && this.tier_familiar == p.get_tier_familiar() && 
                this.tier_marca == p.get_tier_marca() && this.tier_sustentavel == p.get_tier_sustentavel());
    }
}
