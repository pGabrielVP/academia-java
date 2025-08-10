/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.model.dto;

import java.util.List;

/**
 *
 * @author paulo
 */
public class Sublista {

    private String nome;
    private List<ExercicioWrapper> lista_exercicio;
    private List<ExercicioWrapper> superset;

    public Sublista() {
    }

    public Sublista(String nome, List<ExercicioWrapper> lista_exercicio, List<ExercicioWrapper> superset) {
        this.nome = nome;
        this.lista_exercicio = lista_exercicio;
        this.superset = superset;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ExercicioWrapper> getLista_exercicio() {
        return lista_exercicio;
    }

    public void setLista_exercicio(List<ExercicioWrapper> lista_exercicio) {
        this.lista_exercicio = lista_exercicio;
    }

    public List<ExercicioWrapper> getSuperset() {
        return superset;
    }

    public void setSuperset(List<ExercicioWrapper> superset) {
        this.superset = superset;
    }

}
