/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.rotina.paginas;

import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.view.rotina.RotinaMenuLateral;
import com.mycompany.academia.view.rotina.RotinaConteudo;
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
public final class EscolherExercicio extends JPanel {

    private final RotinaMenuLateral parentWindow;
    private final RotinaConteudo rotinaConteudo;
    private List<Exercicio> listaExercicios;

    private GridBagConstraints gridConstraints;
    private JTextField buscar;
    private JButton voltar;
    private JPanel view;
    private JPanel navBar;

    public EscolherExercicio(RotinaMenuLateral parentWindow, RotinaConteudo rotinaConteudo) {
        this.parentWindow = parentWindow;
        this.rotinaConteudo = rotinaConteudo;
        initComponents();
    }

    public void setListaExercicios(List<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
        adicionarBotoes(listaExercicios);
    }

    private void voltarActionPerfomed() {
        parentWindow.showHomePage();
        buscar.setText("");
    }

    private void buscarKeyReleasedEvent() {
        filtrarLista(buscar.getText());
    }

    private void filtrarLista(String filtro) {
        if (!filtro.isBlank()) {
            List<Exercicio> listaFiltrada = new ArrayList<>();
            for (Exercicio exercicio : listaExercicios) {
                if (exercicio.getNomeExercicio().toLowerCase().contains(filtro.toLowerCase())) {
                    listaFiltrada.add(exercicio);
                }
            }
            adicionarBotoes(listaFiltrada);
        } else {
            adicionarBotoes(listaExercicios);
        }
    }

    private Component getFiller(List<Exercicio> listaExercicios) {
        setGridConstraint(1);
        int tamanho_tela = view.getHeight();
        int tamanho_elementos = (listaExercicios.size() + 1) * 100;
        int tamanho_filler = tamanho_tela - tamanho_elementos;
        Box.Filler filler = new Box.Filler(new Dimension(0, 0), new Dimension(0, tamanho_filler), new Dimension(0, 0));
        return filler;
    }

    private void adicionarBotoes(List<Exercicio> listaExercicios) {
        view.removeAll();
        setGridConstraint(0);
        for (Exercicio exercicio : listaExercicios) {
            JButton add_novo = new JButton(exercicio.getNomeExercicio());
            add_novo.setPreferredSize(new Dimension(10, 100));
            add_novo.addActionListener((e) -> {
                rotinaConteudo.adicionarExercicio(exercicio);
            });
            view.add(add_novo, gridConstraints);
        }
        view.add(getFiller(listaExercicios), gridConstraints); // gridbag coloca os itens no centro da tela, esse filler fica no final da lista para puxar os itens para o topo do container
        view.validate();
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
        view = new JPanel(new GridBagLayout());
        navBar = new JPanel();
        voltar = new JButton("Voltar");
        voltar.addActionListener((ActionEvent evt) -> {
            voltarActionPerfomed();
        });
        buscar = new JTextField(12);
        buscar.setMaximumSize(new Dimension(70, 30));
        buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                buscarKeyReleasedEvent();
            }
        });

        BoxLayout boxLayout = new BoxLayout(navBar, BoxLayout.X_AXIS);
        navBar.setLayout(boxLayout);
        navBar.add(voltar);
        navBar.add(Box.createGlue());
        navBar.add(buscar);

        setPreferredSize(new Dimension(370, 551));
        setLayout(new BorderLayout());
        add(navBar, BorderLayout.NORTH);
        add(new JScrollPane(view), BorderLayout.CENTER);
    }

}
