/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.ExerciseController;
import com.mycompany.academia.controller.TargetMuscleController;
import com.mycompany.academia.controller.RoutineController;
import com.mycompany.academia.facade.ExerciseFacade;
import com.mycompany.academia.facade.TargetMuscleFacade;
import com.mycompany.academia.service.Report;
import com.mycompany.academia.view.exercise.ExerciseList;
import com.mycompany.academia.view.exercise.ExerciseTableModel;
import com.mycompany.academia.view.muscle.TargetMuscleList;
import com.mycompany.academia.view.muscle.TargetMuscleTableModel;
import com.mycompany.academia.view.routine.RoutineContent;
import com.mycompany.academia.view.routine.RoutineSideMenu;

import java.awt.BorderLayout;
import java.util.Locale;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

/**
 *
 * @author paulo
 */
public class MainView extends JFrame {

    private final EntityManagerFactory entityManagerFactory;
    private final TargetMuscleFacade targetMuscleFacade;
    private final TargetMuscleTableModel targetMuscleTableModel;
    private final TargetMuscleController targetMuscleController;
    private final ExerciseFacade exerciseFacade;
    private final ExerciseTableModel exerciseTableModel;
    private final ExerciseController exerciseController;
    private final Report report;
    private final RoutineController routineController;
    private final RoutineContent routineContent;
    private final RoutineSideMenu routineSideMenu;
    private JSplitPane jSplitPaneRoutine;
    private TargetMuscleList targetMuscleList;
    private ExerciseList exerciseList;
    private JMenuBar menuBar;

    public MainView() {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU");
        targetMuscleFacade = new TargetMuscleFacade(entityManagerFactory);
        targetMuscleTableModel = new TargetMuscleTableModel();
        targetMuscleController = new TargetMuscleController(targetMuscleFacade, targetMuscleTableModel);

        exerciseFacade = new ExerciseFacade(entityManagerFactory);
        exerciseTableModel = new ExerciseTableModel();
        exerciseController = new ExerciseController(exerciseFacade, targetMuscleFacade, exerciseTableModel);

        report = new Report();
        routineController = new RoutineController(targetMuscleFacade, report);
        routineContent = new RoutineContent(routineController);
        routineSideMenu = new RoutineSideMenu(routineContent, routineController);

        initComponents();
    }

    private void toggleExercisesView() {
        exerciseController.syncExerciseTableModel();
        exerciseList.setLocationRelativeTo(this);
        exerciseList.setVisible(!exerciseList.isVisible());
    }

    private void toggleTargetMusclesView() {
        targetMuscleController.syncTargetMuscleTableModel();
        targetMuscleList.setLocationRelativeTo(this);
        targetMuscleList.setVisible(!targetMuscleList.isVisible());
    }

    private void initComponents() {
        setTitle(LocalizationManager.getString("localization.Strings", "main.view.window.title"));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exerciseList = new ExerciseList(this, exerciseController);
        targetMuscleList = new TargetMuscleList(this, targetMuscleController);
        jSplitPaneRoutine = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, routineSideMenu, routineContent);
        jSplitPaneRoutine.setDividerLocation(400);

        menuBar = new JMenuBar();
        JMenu manageMenu = new JMenu(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.manage.name"));
        JMenu configMenu = new JMenu(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.config.name"));

        JMenuItem menuItemExercicios = new JMenuItem(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.item.exercise"));
        menuItemExercicios.addActionListener((e) -> {
            toggleExercisesView();
        });
        JMenuItem menuItemMusculoAlvo = new JMenuItem(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.item.muscle"));
        menuItemMusculoAlvo.addActionListener((e) -> {
            toggleTargetMusclesView();
        });

        var menuItemLang = new JMenuItem(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.item.language"));
        menuItemLang.addActionListener((e) -> {
            Locale swLocale = Locale.getDefault() == Locale.ENGLISH ? Locale.of("pt", "BR") : Locale.ENGLISH;
            switchLocale(swLocale);
        });

        manageMenu.add(menuItemExercicios);
        manageMenu.add(menuItemMusculoAlvo);

        configMenu.add(menuItemLang);

        menuBar.add(manageMenu);
        menuBar.add(configMenu);

        setJMenuBar(menuBar);
        add(jSplitPaneRoutine, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void switchLocale(Locale locale) {
        LocalizationManager.setLocale(locale);
        java.awt.EventQueue.invokeLater(() -> {
            new MainView().setVisible(true);
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.dispose();
    }

}
