package com.project.aigrocery.models;

public class Users {
    private int id;
    private String nome;
    private String tipo_usuario;
    private Historico90Dias historico_90_dias; 

    // Constructors
    public Users() {
    }
    public Users(int id, String nome, String tipo_usuario, Historico90Dias historico_90_dias) {
        this.id = id;
        this.nome = nome;
        this.tipo_usuario = tipo_usuario;
        this.historico_90_dias = historico_90_dias;
    }




    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public Historico90Dias getHistorico_90_dias() {
        return historico_90_dias;
    }

    public void setHistorico_90_dias(Historico90Dias historico_90_dias) {
        this.historico_90_dias = historico_90_dias;
    }

    // toString method
    @Override
    public String toString() {
        return "Users{" +
                "nome='" + nome + '\'' +
                ", tipo_usuario='" + tipo_usuario + '\'' +
                ", historico_90_dias=" + historico_90_dias +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Users users = (Users) o;

        if (nome != null ? !nome.equals(users.nome) : users.nome != null) return false;
        if (tipo_usuario != null ? !tipo_usuario.equals(users.tipo_usuario) : users.tipo_usuario != null) return false;
        return historico_90_dias != null ? historico_90_dias.equals(users.historico_90_dias) : users.historico_90_dias == null;
    }

    @Override
    public int hashCode() {
        int result = nome != null ? nome.hashCode() : 0;
        result = 31 * result + (tipo_usuario != null ? tipo_usuario.hashCode() : 0);
        result = 31 * result + (historico_90_dias != null ? historico_90_dias.hashCode() : 0);
        return result;
    }
}
