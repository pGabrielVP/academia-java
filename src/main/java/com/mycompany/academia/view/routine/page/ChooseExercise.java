/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.routine.page;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.model.entity.Exercicio;
import com.mycompany.academia.view.routine.RoutineContent;
import com.mycompany.academia.view.routine.RoutineSideMenu;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author paulo
 */
public final class ChooseExercise extends JPanel {

    private final RoutineSideMenu parentWindow;
    private final RoutineContent routineContent;
    private List<Exercicio> exercisesList;

    private GridBagConstraints gridConstraints;
    private JTextField jTextFieldSearchTerm;
    private JButton jButtonReturn;
    private JPanel jPanelView;
    private JPanel jPanelNavBar;

    public ChooseExercise(RoutineSideMenu parentWindow, RoutineContent routineContent) {
        this.parentWindow = parentWindow;
        this.routineContent = routineContent;
        initComponents();
    }

    public void setExercisesList(List<Exercicio> exercisesList) {
        this.exercisesList = exercisesList;
        addButtons(exercisesList);
    }

    private void returnActionPerfomed() {
        parentWindow.showChooseTargetMuscle();
        jTextFieldSearchTerm.setText("");
    }

    private void searchFieldKeyReleasedEvent() {
        filterList(jTextFieldSearchTerm.getText());
    }

    private void filterList(String filterTerm) {
        if (!filterTerm.isBlank()) {
            List<Exercicio> filteredList = new ArrayList<>();
            for (Exercicio exercise : exercisesList) {
                if (exercise.getNomeExercicio().toLowerCase().contains(filterTerm.toLowerCase())) {
                    filteredList.add(exercise);
                }
            }
            addButtons(filteredList);
        } else {
            addButtons(exercisesList);
        }
    }

    private Component getFiller(List<Exercicio> exercisesList) {
        setGridConstraint(1);
        int screenHeight = jPanelView.getHeight();
        int elementsHeight = (exercisesList.size() + 1) * 100;
        int fillerHeight = screenHeight - elementsHeight;
        return new Box.Filler(new Dimension(0, 0), new Dimension(0, fillerHeight), new Dimension(0, 0));
    }

    private void addButtons(List<Exercicio> exercisesList) {
        jPanelView.removeAll();
        setGridConstraint(0);
        for (Exercicio exercise : exercisesList) {
            JButton jButtonExercise = new JButton(exercise.getNomeExercicio());
            jButtonExercise.setPreferredSize(new Dimension(10, 100));
            jButtonExercise.addActionListener((e) -> {
                routineContent.addExercise(exercise);
            });
            jPanelView.add(jButtonExercise, gridConstraints);
        }
        jPanelView.add(getFiller(exercisesList), gridConstraints); // gridbag coloca os itens no centro da tela, esse
                                                                   // filler
        // fica no final da lista para puxar os itens para o topo
        // do container
        jPanelView.validate();
    }

    private void setGridConstraint(int weighty) {
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.weighty = weighty;
        gridConstraints.weightx = 1;
        gridConstraints.gridx = 0;
    }

    private void initComponents() {
        gridConstraints = new GridBagConstraints();
        jPanelView = new JPanel(new GridBagLayout());
        jPanelNavBar = new JPanel();
        jButtonReturn = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.pages.choose.exercise.button.return"));
        jButtonReturn.addActionListener((ActionEvent evt) -> {
            returnActionPerfomed();
        });
        jTextFieldSearchTerm = new JTextField(12);
        jTextFieldSearchTerm.setMaximumSize(new Dimension(70, 30));
        jTextFieldSearchTerm.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                searchFieldKeyReleasedEvent();
            }
        });

        BoxLayout boxLayout = new BoxLayout(jPanelNavBar, BoxLayout.X_AXIS);
        jPanelNavBar.setLayout(boxLayout);
        jPanelNavBar.add(jButtonReturn);
        jPanelNavBar.add(Box.createGlue());
        jPanelNavBar.add(jTextFieldSearchTerm);

        setPreferredSize(new Dimension(370, 551));
        setLayout(new BorderLayout());
        add(jPanelNavBar, BorderLayout.NORTH);
        add(new JScrollPane(jPanelView), BorderLayout.CENTER);
    }

}
