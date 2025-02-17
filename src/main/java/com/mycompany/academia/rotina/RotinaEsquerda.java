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
public class RotinaEsquerda extends JPanel {

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

    private final RotinaDireita rotina_direita;

    public RotinaEsquerda(RotinaDireita rotinaDireita) {
        rotina_direita = rotinaDireita;

        cLayout = new CardLayout();
        setLayout(cLayout);
        setPreferredSize(new Dimension(370, 551));
        add(HOMEPAGE, new HomePage(this));
        add(BICEPS, new Biceps(this, rotina_direita));
        add(TRICEPS, new Triceps(this, rotina_direita));
        add(ANTEBRACO, new Antebraco(this, rotina_direita));
        add(OMBRO, new Ombro(this, rotina_direita));
        add(PEITO, new Peito(this, rotina_direita));
        add(BARRIGA, new Abdomen(this, rotina_direita));
        add(COSTA, new Costa(this, rotina_direita));
        add(PERNA, new Perna(this, rotina_direita));
        add(CARDIO, new Cardio(this, rotina_direita));
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
