/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.rotina;

import com.mycompany.academia.controle.RotinaControle;
import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.view.rotina.paginas.EscolherMusculoAlvo;
import com.mycompany.academia.view.rotina.paginas.EscolherExercicio;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author paulo
 */
public final class RotinaMenuLateral extends JPanel {

    private final String HOMEPAGE = "home_page";
    private final String LISTAR_EXERCICIOS = "listar_exercicios";
    private final EscolherExercicio exerciciosListar;
    private final EscolherMusculoAlvo homepage;
    private final CardLayout cLayout;

    public RotinaMenuLateral(RotinaConteudo siblingPanel, RotinaControle rotinaControle) {
        exerciciosListar = new EscolherExercicio(this, siblingPanel);
        homepage = new EscolherMusculoAlvo(this, rotinaControle);
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
