/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina.paginas;

import com.mycompany.academia.rotina.RotinaEsquerda;
import com.mycompany.academia.rotina.RotinaDireita;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author paulo
 */
public class Peito extends javax.swing.JPanel {

    private final List<String> exerciciosPeito = Arrays.asList(
            "Supino Reto com Barra",
            "Supino Inclinado com Barra",
            "Supino Declinado com Barra",
            "Supino Reto com Halteres",
            "Supino Inclinado com Halteres",
            "Supino Declinado com Halteres",
            "Crucifixo Reto",
            "Crucifixo Inclinado",
            "Crossover no Pulley Alto",
            "Crossover no Pulley Baixo"
    );
    private final RotinaEsquerda parent;
    private final RotinaDireita rotina_direita;

    /**
     * Creates new form Imagens
     *
     * @param parent_window
     * @param rotinaDireita
     */
    public Peito(RotinaEsquerda parent_window, RotinaDireita rotinaDireita) {
        parent = parent_window;
        rotina_direita = rotinaDireita;

        initComponents();
        botaoVoltar.addActionListener((e) -> {
            parent.showHomePage();
        });

        for (String exercicio : exerciciosPeito) {
            JButton add_novo = new JButton(exercicio);
            add_novo.addActionListener((e) -> {
                int guia = rotina_direita.getPainel_lista().getSelectedIndex();
                rotina_direita.getModel_lista().get(guia).addElement(exercicio);
            });
            jPanel1.add(add_novo);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pesquisarEntrada = new javax.swing.JTextField();
        botaoVoltar = new javax.swing.JButton();
        botaoPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(370, 551));

        pesquisarEntrada.setColumns(12);

        botaoVoltar.setText("Voltar");

        botaoPesquisar.setText("Pesquisar");

        jPanel1.setLayout(new java.awt.GridLayout(3, 2));
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoVoltar)
                .addGap(74, 74, 74)
                .addComponent(pesquisarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(botaoPesquisar)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pesquisarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoVoltar)
                    .addComponent(botaoPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoPesquisar;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField pesquisarEntrada;
    // End of variables declaration//GEN-END:variables
}
