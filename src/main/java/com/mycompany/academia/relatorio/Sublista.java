/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.relatorio;

import com.mycompany.academia.entidades.Exercicio;
import java.util.List;

/**
 *
 * @author paulo
 */
public class Sublista {

    private String nome;
    private List<Exercicio> lista_exercicio;
    private List<Exercicio> superset;

    public Sublista() {
    }

    public Sublista(String nome, List<Exercicio> lista_exercicio, List<Exercicio> superset) {
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

    public List<Exercicio> getLista_exercicio() {
        return lista_exercicio;
    }

    public void setLista_exercicio(List<Exercicio> lista_exercicio) {
        this.lista_exercicio = lista_exercicio;
    }

    public List<Exercicio> getSuperset() {
        return superset;
    }

    public void setSuperset(List<Exercicio> superset) {
        this.superset = superset;
    }

}
