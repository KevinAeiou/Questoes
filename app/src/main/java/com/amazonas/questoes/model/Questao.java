package com.amazonas.questoes.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Questao implements Serializable {
    private String id;
    private String descricao;
    private String banca;
    private int ano;
    private String tipo;
    private ArrayList<Alternativa> alternativas;
    private String gabarito;
    private String dificudade;

    private ArrayList<Comentario> comentarios;
    public Questao() {
        id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getBanca() {
        return banca;
    }

    public void setBanca(String banca) {
        this.banca = banca;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Alternativa> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(ArrayList<Alternativa> alternativas) {
        this.alternativas = alternativas;
    }

    public String getGabarito() {
        return gabarito;
    }

    public void setGabarito(String gabarito) {
        this.gabarito = gabarito;
    }

    public String getDificudade() {
        return dificudade;
    }

    public void setDificudade(String dificudade) {
        this.dificudade = dificudade;
    }

    public boolean acertou(String idAlternativa) {
        return gabarito.equals(idAlternativa);
    }

    public ArrayList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
