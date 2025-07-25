/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina.paginas;

import com.mycompany.academia.rotina.RotinaTablePanel;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.rotina.RotinaMenuLateral;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JButton;

/**
 *
 * @author paulo
 */
public class ExerciciosListar extends javax.swing.JPanel {

    private final GridBagConstraints gridConstraints = new GridBagConstraints();
    private final RotinaTablePanel rotina_table_panel_;
    private final RotinaMenuLateral parent_window_;
    private List<Exercicio> lista_exercicios;

    /**
     * Creates new form Imagens
     *
     * @param parent_window
     * @param rotina_table_panel
     */
    public ExerciciosListar(RotinaMenuLateral parent_window, RotinaTablePanel rotina_table_panel) {
        rotina_table_panel_ = rotina_table_panel;
        parent_window_ = parent_window;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filtro_pesquisa = new javax.swing.JTextField();
        botaoVoltar = new javax.swing.JButton();
        botaoPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(370, 551));

        filtro_pesquisa.setColumns(12);
        filtro_pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filtro_pesquisaKeyReleased(evt);
            }
        });

        botaoVoltar.setText("Voltar");
        botaoVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoVoltarActionPerformed(evt);
            }
        });

        botaoPesquisar.setText("Pesquisar");

        jPanel1.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(botaoVoltar)
                .addGap(74, 74, 74)
                .addComponent(filtro_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                .addComponent(botaoPesquisar)
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtro_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botaoVoltar)
                    .addComponent(botaoPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoVoltarActionPerformed
        parent_window_.showHomePage();
        filtro_pesquisa.setText("");
    }//GEN-LAST:event_botaoVoltarActionPerformed

    private void filtro_pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filtro_pesquisaKeyReleased
        filtrarLista(filtro_pesquisa.getText());
    }//GEN-LAST:event_filtro_pesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoPesquisar;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JTextField filtro_pesquisa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setExercicios(List<Exercicio> exercicios) {
        lista_exercicios = exercicios;
        adicionarBotoes(exercicios);
    }

    private void adicionarBotoes(List<Exercicio> exercicios) {
        jPanel1.removeAll();
        setGridConstraint(0);
        for (Exercicio exercicio : exercicios) {
            JButton add_novo = new JButton(exercicio.getNomeExercicio());
            add_novo.setPreferredSize(new Dimension(10, 100));
            add_novo.addActionListener((e) -> {
                rotina_table_panel_.adicionar_exercicio(exercicio);
            });
            jPanel1.add(add_novo, gridConstraints);
        }
        jPanel1.add(getFiller(exercicios), gridConstraints); // gridbag coloca os itens no centro da tela, esse filler fica no final da lista para puxar os itens para o topo do container
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private void filtrarLista(String filtro) {
        if (!filtro.isBlank()) {
            List<Exercicio> lista_filtrada = new ArrayList<>();
            for (Exercicio exercicio : lista_exercicios) {
                if (exercicio.getNomeExercicio().toLowerCase().contains(filtro.toLowerCase())) {
                    lista_filtrada.add(exercicio);
                }
            }
            adicionarBotoes(lista_filtrada);
        } else {
            adicionarBotoes(lista_exercicios);
        }
    }

    private void setGridConstraint(int weighty) {
        gridConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.weighty = weighty;
        gridConstraints.weightx = 1;
        gridConstraints.gridx = 0;
    }

    private Box.Filler getFiller(List<Exercicio> exercicios) {
        setGridConstraint(1);
        int tamanho_tela = jPanel1.getHeight();
        int tamanho_elementos = (exercicios.size() + 1) * 100;
        int tamanho_filler = tamanho_tela - tamanho_elementos;
        Box.Filler filler = new Box.Filler(new Dimension(0, 0), new Dimension(0, tamanho_filler), new Dimension(0, 0));
        return filler;
    }

}
