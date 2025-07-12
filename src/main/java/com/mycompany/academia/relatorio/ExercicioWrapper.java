/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.relatorio;

import com.mycompany.academia.entidades.Exercicio;

/**
 *
 * @author paulo
 */
public class ExercicioWrapper {

    private Exercicio exercicio;
    private int reps;
    private int sets;
    private int descanso;

    public ExercicioWrapper() {
    }

    public ExercicioWrapper(Exercicio exercicio, int reps, int sets, int descanso) {
        this.exercicio = exercicio;
        this.reps = reps;
        this.sets = sets;
        this.descanso = descanso;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getDescanso() {
        return descanso;
    }

    public void setDescanso(int descanso) {
        this.descanso = descanso;
    }

}
