/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.routine;

import com.mycompany.academia.controller.RoutineController;
import com.mycompany.academia.model.entity.Exercicio;
import com.mycompany.academia.view.routine.page.ChooseExercise;
import com.mycompany.academia.view.routine.page.ChooseTargetMuscle;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author paulo
 */
public final class RoutineSideMenu extends JPanel {

    private final String CHOOSE_TARGET_MUSCLE = "choose_target_muscle";
    private final String CHOOSE_EXERCISE = "choose_exercise";
    private final ChooseExercise chooseExercise;
    private final ChooseTargetMuscle chooseTargetMuscle;
    private final CardLayout cLayout;

    public RoutineSideMenu(RoutineContent siblingPanel, RoutineController routineController) {
        chooseExercise = new ChooseExercise(this, siblingPanel);
        chooseTargetMuscle = new ChooseTargetMuscle(this, routineController);
        cLayout = new CardLayout();

        setLayout(cLayout);
        setPreferredSize(new Dimension(370, 551));
        add(CHOOSE_TARGET_MUSCLE, chooseTargetMuscle);
        add(CHOOSE_EXERCISE, chooseExercise);
    }

    public void showChooseTargetMuscle() {
        chooseTargetMuscle.update();
        cLayout.show(this, CHOOSE_TARGET_MUSCLE);
    }

    public void showListExercises(List<Exercicio> exerciseList) {
        chooseExercise.setExercisesList(exerciseList);
        cLayout.show(this, CHOOSE_EXERCISE);
    }

}
