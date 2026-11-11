/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controller;

import com.mycompany.academia.facade.TargetMuscleFacade;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.view.muscle.TargetMuscleTableModel;

import java.util.List;

/**
 *
 * @author paulo
 */
public class TargetMuscleController {

    private final TargetMuscleFacade targetMuscleFacade;
    private final TargetMuscleTableModel targetMuscleTableModel;

    public TargetMuscleController(TargetMuscleFacade targetMuscleFacade,
            TargetMuscleTableModel targetMuscleTableModel) {
        this.targetMuscleFacade = targetMuscleFacade;
        this.targetMuscleTableModel = targetMuscleTableModel;
    }

    public void syncTargetMuscleTableModel() {
        targetMuscleTableModel.sync(getTargetMuscleList());
    }

    public List<MusculoAlvo> getTargetMuscleList() {
        return targetMuscleFacade.listAll();
    }

    public MusculoAlvo findById(Integer id) {
        return targetMuscleFacade.find(id);
    }

    public void save(MusculoAlvo targetMuscle) {
        targetMuscleFacade.save(targetMuscle);
        targetMuscleTableModel.offer(targetMuscle);
    }

    public void delete(MusculoAlvo targetMuscle) {
        targetMuscleFacade.delete(targetMuscle);
        targetMuscleTableModel.delete(targetMuscle);
    }

    public TargetMuscleTableModel getTargetMuscleTableModel() {
        return targetMuscleTableModel;
    }

}
