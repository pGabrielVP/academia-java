/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.rotina;

import com.mycompany.academia.rotina.paginas.Abdomen;
import com.mycompany.academia.rotina.paginas.Antebraco;
import com.mycompany.academia.rotina.paginas.HomePage;
import com.mycompany.academia.rotina.paginas.Biceps;
import com.mycompany.academia.rotina.paginas.Cardio;
import com.mycompany.academia.rotina.paginas.Costa;
import com.mycompany.academia.rotina.paginas.Ombro;
import com.mycompany.academia.rotina.paginas.Peito;
import com.mycompany.academia.rotina.paginas.Perna;
import com.mycompany.academia.rotina.paginas.Triceps;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author paulo
 */
public class RotinaMenuLateral extends JPanel {

    // paginas do aplicativo
    public final String HOMEPAGE = "home_page";
    public final String BICEPS = "biceps";
    public final String TRICEPS = "triceps";
    public final String ANTEBRACO = "ante_braco";
    public final String OMBRO = "ombro";
    public final String PEITO = "peito";
    public final String BARRIGA = "barriga";
    public final String COSTA = "costa";
    public final String PERNA = "perna";
    public final String CARDIO = "cardio";
    // layout
    private final CardLayout cLayout;

    private final RotinaTablePanel _rotina_table_panel;

    public RotinaMenuLateral(RotinaTablePanel rotina_table_panel) {
        _rotina_table_panel = rotina_table_panel;

        cLayout = new CardLayout();
        setLayout(cLayout);
        setPreferredSize(new Dimension(370, 551));
        add(HOMEPAGE, new HomePage(this));
        add(BICEPS, new Biceps(this, _rotina_table_panel));
        add(TRICEPS, new Triceps(this, _rotina_table_panel));
        add(ANTEBRACO, new Antebraco(this, _rotina_table_panel));
        add(OMBRO, new Ombro(this, _rotina_table_panel));
        add(PEITO, new Peito(this, _rotina_table_panel));
        add(BARRIGA, new Abdomen(this, _rotina_table_panel));
        add(COSTA, new Costa(this, _rotina_table_panel));
        add(PERNA, new Perna(this, _rotina_table_panel));
        add(CARDIO, new Cardio(this, _rotina_table_panel));
    }

    public void showHomePage() {
        cLayout.show(this, HOMEPAGE);
    }

    public void showBiceps() {
        cLayout.show(this, BICEPS);
    }

    public void showTriceps() {
        cLayout.show(this, TRICEPS);
    }

    public void showAntebraco() {
        cLayout.show(this, ANTEBRACO);
    }

    public void showOmbro() {
        cLayout.show(this, OMBRO);
    }

    public void showPeito() {
        cLayout.show(this, PEITO);
    }

    public void showAbdomen() {
        cLayout.show(this, BARRIGA);
    }

    public void showCosta() {
        cLayout.show(this, COSTA);
    }

    public void showPerna() {
        cLayout.show(this, PERNA);
    }

    public void showCardio() {
        cLayout.show(this, CARDIO);
    }

}
