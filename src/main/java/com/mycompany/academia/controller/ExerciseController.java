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

    private final ExerciseFacade exerciseFacade;
    private final TargetMuscleFacade targetMuscleFacade;
    private final ExerciseTableModel exerciseTableModel;

    public ExerciseController(ExerciseFacade exerciseFacade, TargetMuscleFacade targetMuscleFacade,
            ExerciseTableModel exerciseTableModel) {
        this.exerciseFacade = exerciseFacade;
        this.targetMuscleFacade = targetMuscleFacade;
        this.exerciseTableModel = exerciseTableModel;
    }

    public void syncExerciseTableModel() {
        exerciseTableModel.sync(getExerciseList());
    }

    public List<Exercicio> getExerciseList() {
        return exerciseFacade.listAll();
    }

    public List<MusculoAlvo> getTargetMuscleList() {
        return targetMuscleFacade.listAll();
    }

    public Exercicio findById(Integer id) {
        return exerciseFacade.find(id);
    }

    public void save(Exercicio exercise) {
        exerciseFacade.save(exercise);
        exerciseTableModel.offer(exercise);
    }

    public void delete(Exercicio exercise) {
        exerciseFacade.delete(exercise);
        exerciseTableModel.delete(exercise);
    }

    public ExerciseTableModel getExerciseTableModel() {
        return exerciseTableModel;
    }

}
