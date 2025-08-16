/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.facade.ExercicioFacade;
import com.mycompany.academia.model.entidades.Exercicio;
import java.util.List;

/**
 *
 * @author paulo
 */
public class ExercicioControle {

    private final ExercicioFacade exercicioFacade;

    public ExercicioControle() {
        exercicioFacade = new ExercicioFacade();
    }

    public List<Exercicio> getListaExercicio() {
        return exercicioFacade.listaTodos();
    }

    public Exercicio buscar(Integer id) {
        return exercicioFacade.buscar(id);
    }

    public void salvar(Exercicio exercicio) {
        exercicioFacade.salvar(exercicio);
    }

    public void excluir(Exercicio exercicio) {
        exercicioFacade.remover(exercicio);
    }

}
