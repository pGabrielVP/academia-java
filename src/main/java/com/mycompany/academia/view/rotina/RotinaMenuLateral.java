/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.rotina;

import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.view.rotina.paginas.HomePage;
import com.mycompany.academia.view.rotina.paginas.ExerciciosListar;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author paulo
 */
public class RotinaMenuLateral extends JPanel {

    private final String HOMEPAGE = "home_page";
    private final String LISTAR_EXERCICIOS = "listar_exercicios";
    private final ExerciciosListar painel_exercicios;
    private final HomePage painel_homepage;
    private final CardLayout cLayout;

    public RotinaMenuLateral(RotinaTablePanel rotina_table_panel) {
        painel_exercicios = new ExerciciosListar(this, rotina_table_panel);
        painel_homepage = new HomePage(this);
        cLayout = new CardLayout();

        setLayout(cLayout);
        setPreferredSize(new Dimension(370, 551));
        add(HOMEPAGE, painel_homepage);
        add(LISTAR_EXERCICIOS, painel_exercicios);
    }

    public void showHomePage() {
        painel_homepage.atualizar(this);
        cLayout.show(this, HOMEPAGE);
    }

    public void showListarExercicios(List<Exercicio> lista_exercicio) {
        painel_exercicios.setExercicios(lista_exercicio);
        cLayout.show(this, LISTAR_EXERCICIOS);
    }

}
