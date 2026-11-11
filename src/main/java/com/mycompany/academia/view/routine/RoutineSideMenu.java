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

    private final String HOMEPAGE = "home_page";
    private final String LISTAR_EXERCICIOS = "listar_exercicios";
    private final ChooseExercise exerciciosListar;
    private final ChooseTargetMuscle homepage;
    private final CardLayout cLayout;

    public RoutineSideMenu(RoutineContent siblingPanel, RoutineController rotinaControle) {
        exerciciosListar = new ChooseExercise(this, siblingPanel);
        homepage = new ChooseTargetMuscle(this, rotinaControle);
        cLayout = new CardLayout();

        setLayout(cLayout);
        setPreferredSize(new Dimension(370, 551));
        add(HOMEPAGE, homepage);
        add(LISTAR_EXERCICIOS, exerciciosListar);
    }

    public void showHomePage() {
        homepage.atualizar();
        cLayout.show(this, HOMEPAGE);
    }

    public void showListarExercicios(List<Exercicio> lista_exercicio) {
        exerciciosListar.setListaExercicios(lista_exercicio);
        cLayout.show(this, LISTAR_EXERCICIOS);
    }

}
