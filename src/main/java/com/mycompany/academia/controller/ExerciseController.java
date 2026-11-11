/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controller;

import com.mycompany.academia.facade.ExerciseFacade;
import com.mycompany.academia.facade.TargetMuscleFacade;
import com.mycompany.academia.model.entity.Exercicio;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.view.exercise.ExerciseTableModel;

import java.util.List;

/**
 *
 * @author paulo
 */
public class ExerciseController {

    private final ExerciseFacade exercicioFacade;
    private final TargetMuscleFacade musculoAlvoFacade;
    private final ExerciseTableModel exercicioTableModel;

    public ExerciseController(ExerciseFacade exercicioFacade, TargetMuscleFacade musculoAlvoFacade, ExerciseTableModel exercicioTableModel) {
        this.exercicioFacade = exercicioFacade;
        this.musculoAlvoFacade = musculoAlvoFacade;
        this.exercicioTableModel = exercicioTableModel;
    }

    public void sincronizarExercicioTableModel() {
        exercicioTableModel.sincronizar(getListaExercicio());
    }

    public List<Exercicio> getListaExercicio() {
        return exercicioFacade.listaTodos();
    }

    public List<MusculoAlvo> getListaMusculoAlvo() {
        return musculoAlvoFacade.listaTodos();
    }

    public Exercicio buscar(Integer id) {
        return exercicioFacade.buscar(id);
    }

    public void salvar(Exercicio exercicio) {
        exercicioFacade.salvar(exercicio);
        exercicioTableModel.oferecer(exercicio);
    }

    public void excluir(Exercicio exercicio) {
        exercicioFacade.remover(exercicio);
        exercicioTableModel.deletar(exercicio);
    }

    public ExerciseTableModel getExercicioTableModel() {
        return exercicioTableModel;
    }

}
