/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.ExercicioControle;
import com.mycompany.academia.controller.MusculoAlvoControle;
import com.mycompany.academia.controller.RotinaControle;
import com.mycompany.academia.facade.ExercicioFacade;
import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.service.Relatorio;
import com.mycompany.academia.view.exercise.ExercicioLista;
import com.mycompany.academia.view.exercise.ExercicioTableModel;
import com.mycompany.academia.view.muscle.MusculoAlvoLista;
import com.mycompany.academia.view.muscle.MusculoAlvoTableModel;
import com.mycompany.academia.view.routine.RotinaConteudo;
import com.mycompany.academia.view.routine.RotinaMenuLateral;

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
    private final MusculoAlvoFacade musculoAlvoFacade;
    private final MusculoAlvoTableModel musculoAlvoTableModel;
    private final MusculoAlvoControle musculoAlvoControle;
    private final ExercicioFacade exercicioFacade;
    private final ExercicioTableModel exercicioTableModel;
    private final ExercicioControle exercicioControle;
    private final Relatorio relatorio;
    private final RotinaControle rotinaControle;
    private final RotinaConteudo rotinaConteudo;
    private final RotinaMenuLateral rotinaMenuLateral;

    private JSplitPane rotina;
    private MusculoAlvoLista musculoalvoLista;
    private ExercicioLista exercicioLista;
    private JMenuBar menuBar;

    public MainView() {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU");
        musculoAlvoFacade = new MusculoAlvoFacade(entityManagerFactory);
        musculoAlvoTableModel = new MusculoAlvoTableModel();
        musculoAlvoControle = new MusculoAlvoControle(musculoAlvoFacade, musculoAlvoTableModel);

        exercicioFacade = new ExercicioFacade(entityManagerFactory);
        exercicioTableModel = new ExercicioTableModel();
        exercicioControle = new ExercicioControle(exercicioFacade, musculoAlvoFacade, exercicioTableModel);

        relatorio = new Relatorio();
        rotinaControle = new RotinaControle(musculoAlvoFacade, relatorio);
        rotinaConteudo = new RotinaConteudo(rotinaControle);
        rotinaMenuLateral = new RotinaMenuLateral(rotinaConteudo, rotinaControle);

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

        exercicioLista = new ExercicioLista(this, exercicioControle);
        musculoalvoLista = new MusculoAlvoLista(this, musculoAlvoControle);
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
