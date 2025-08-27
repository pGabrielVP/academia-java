/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view;

import com.mycompany.academia.controle.ExercicioControle;
import com.mycompany.academia.controle.MusculoAlvoControle;
import com.mycompany.academia.controle.RotinaControle;
import com.mycompany.academia.facade.ExercicioFacade;
import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.services.Relatorio;
import com.mycompany.academia.view.exercicio.ExercicioLista;
import com.mycompany.academia.view.exercicio.ExercicioTableModel;
import com.mycompany.academia.view.musculoalvo.MusculoAlvoLista;
import com.mycompany.academia.view.musculoalvo.MusculoAlvoTableModel;
import com.mycompany.academia.view.rotina.RotinaConteudo;
import com.mycompany.academia.view.rotina.RotinaMenuLateral;
import java.awt.BorderLayout;
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
        setTitle("Rotinas");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        exercicioLista = new ExercicioLista(this, exercicioControle);
        musculoalvoLista = new MusculoAlvoLista(this, musculoAlvoControle);
        rotina = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rotinaMenuLateral, rotinaConteudo);
        rotina.setDividerLocation(400);

        menuBar = new JMenuBar();
        JMenu menu = new JMenu("Gerenciar");

        JMenuItem menuExercicios = new JMenuItem("Exercícios");
        menuExercicios.addActionListener((e) -> {
            toggleExercicios();
        });
        JMenuItem menuMusculoAlvo = new JMenuItem("Músculos-Alvo");
        menuMusculoAlvo.addActionListener((e) -> {
            toggleMusculosAlvo();
        });

        menu.add(menuExercicios);
        menu.add(menuMusculoAlvo);

        menuBar.add(menu);

        setJMenuBar(menuBar);
        add(rotina, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

}
