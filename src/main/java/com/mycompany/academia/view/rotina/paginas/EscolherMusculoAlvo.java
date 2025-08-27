/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.rotina.paginas;

import com.mycompany.academia.controle.RotinaControle;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.view.rotina.RotinaMenuLateral;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author paulo
 */
public final class EscolherMusculoAlvo extends JScrollPane {

    private final RotinaControle rotinaControle;
    private final RotinaMenuLateral parentWindow;
    private List<MusculoAlvo> listaMusculoAlvo;

    private JPanel view;

    public EscolherMusculoAlvo(RotinaMenuLateral parentWindow, RotinaControle rotinaControle) {
        this.parentWindow = parentWindow;
        this.rotinaControle = rotinaControle;
        initComponents();
    }

    public void atualizar() {
        listaMusculoAlvo = rotinaControle.listaTodosMusculoAlvo();
        adicionarBotoes();
    }

    private void adicionarBotoes() {
        view.removeAll();
        listaMusculoAlvo.forEach((musculo_alvo) -> {
            JButton botao = new JButton(musculo_alvo.getNomeAlvo());
            botao.addActionListener((e) -> {
                parentWindow.showListarExercicios(musculo_alvo.getExercicioList());
            });
            view.add(botao);
        });
    }

    private void initComponents() {
        int colunas = 3;
        view = new JPanel(new GridLayout(0, colunas));
        setPreferredSize(new Dimension(370, 551));
        setViewportView(view);
        atualizar();
    }

}
