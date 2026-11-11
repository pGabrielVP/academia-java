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
    private final TargetMuscleFacade musculoAlvoFacade;
    private final TargetMuscleTableModel musculoAlvoTableModel;
    private final TargetMuscleController musculoAlvoControle;
    private final ExerciseFacade exercicioFacade;
    private final ExerciseTableModel exercicioTableModel;
    private final ExerciseController exercicioControle;
    private final Report relatorio;
    private final RoutineController rotinaControle;
    private final RoutineContent rotinaConteudo;
    private final RoutineSideMenu rotinaMenuLateral;

    private JSplitPane rotina;
    private TargetMuscleList musculoalvoLista;
    private ExerciseList exercicioLista;
    private JMenuBar menuBar;

    public MainView() {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU");
        musculoAlvoFacade = new TargetMuscleFacade(entityManagerFactory);
        musculoAlvoTableModel = new TargetMuscleTableModel();
        musculoAlvoControle = new TargetMuscleController(musculoAlvoFacade, musculoAlvoTableModel);

        exercicioFacade = new ExerciseFacade(entityManagerFactory);
        exercicioTableModel = new ExerciseTableModel();
        exercicioControle = new ExerciseController(exercicioFacade, musculoAlvoFacade, exercicioTableModel);

        relatorio = new Report();
        rotinaControle = new RoutineController(musculoAlvoFacade, relatorio);
        rotinaConteudo = new RoutineContent(rotinaControle);
        rotinaMenuLateral = new RoutineSideMenu(rotinaConteudo, rotinaControle);

        initComponents();
    }

    private void toggleExercicios() {
        exercicioControle.sincronizarExercicioTableModel();
        exercicioLista.setLocationRelativeTo(this);
        exercicioLista.setVisible(!exercicioLista.isVisible());
    }

    private void toggleMusculosAlvo() {
        musculoAlvoControle.sincronizarMusculoAlvoTableModel();
        musculoalvoLista.setLocationRelativeTo(this);
        musculoalvoLista.setVisible(!musculoalvoLista.isVisible());
    }

    private void initComponents() {
        setTitle(LocalizationManager.getString("localization.Strings", "main.view.window.title"));
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exercicioLista = new ExerciseList(this, exercicioControle);
        musculoalvoLista = new TargetMuscleList(this, musculoAlvoControle);
        rotina = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rotinaMenuLateral, rotinaConteudo);
        rotina.setDividerLocation(400);

        menuBar = new JMenuBar();
        JMenu manageMenu = new JMenu(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.manage.name"));
        JMenu configMenu = new JMenu(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.config.name"));

        JMenuItem menuItemExercicios = new JMenuItem(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.item.exercise"));
        menuItemExercicios.addActionListener((e) -> {
            toggleExercicios();
        });
        JMenuItem menuItemMusculoAlvo = new JMenuItem(
                LocalizationManager.getString("localization.Strings", "main.view.jmenu.item.muscle"));
        menuItemMusculoAlvo.addActionListener((e) -> {
            toggleMusculosAlvo();
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
        add(rotina, BorderLayout.CENTER);
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
