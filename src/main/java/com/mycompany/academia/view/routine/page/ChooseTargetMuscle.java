/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.routine.page;

import com.mycompany.academia.controller.RoutineController;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.view.routine.RoutineSideMenu;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author paulo
 */
public final class ChooseTargetMuscle extends JScrollPane {

    private final RoutineController routineController;
    private final RoutineSideMenu parentWindow;
    private List<MusculoAlvo> targetMuscleLIst;
    private JPanel view;

    public ChooseTargetMuscle(RoutineSideMenu parentWindow, RoutineController routineController) {
        this.parentWindow = parentWindow;
        this.routineController = routineController;
        initComponents();
    }

    public void update() {
        targetMuscleLIst = routineController.listAllTargetMuscle();
        addButtons();
    }

    private void addButtons() {
        view.removeAll();
        targetMuscleLIst.forEach((targetMuscle) -> {
            JButton jButtonTargetMuscle = new JButton(targetMuscle.getNomeAlvo());
            jButtonTargetMuscle.addActionListener((e) -> {
                parentWindow.showListExercises(targetMuscle.getExercicioList());
            });
            view.add(jButtonTargetMuscle);
        });
    }

    private void initComponents() {
        int columns = 3;
        view = new JPanel(new GridLayout(0, columns));
        setPreferredSize(new Dimension(370, 551));
        setViewportView(view);
        update();
    }

}
