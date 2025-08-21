/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.facade.ExercicioFacade;
import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.view.exercicio.ExercicioTableModel;
import java.util.List;

/**
 *
 * @author paulo
 */
public class ExercicioControle {

    private final ExercicioFacade exercicioFacade;
    private final MusculoAlvoFacade musculoAlvoFacade;
    private final ExercicioTableModel exercicioTableModel;

    public ExercicioControle(ExercicioFacade exercicioFacade, MusculoAlvoFacade musculoAlvoFacade, ExercicioTableModel exercicioTableModel) {
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

    public ExercicioTableModel getExercicioTableModel() {
        return exercicioTableModel;
    }

}
