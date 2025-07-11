/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina.paginas;

import com.mycompany.academia.rotina.RotinaTablePanel;
import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.controle.MusculoAlvoJpaController;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.MusculoAlvo;
import com.mycompany.academia.rotina.RotinaMenuLateral;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.JButton;

/**
 *
 * @author paulo
 */
public class Ombro extends javax.swing.JPanel {

    ExercicioJpaController exercicioJpaController = new ExercicioJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    MusculoAlvoJpaController alvoJpaController = new MusculoAlvoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));

    private final MusculoAlvo ma = alvoJpaController.findMusculoAlvo(7);
    private final List<Exercicio> lista_exercicios = exercicioJpaController.find_exercicios_where_musculo(ma);

    private final RotinaMenuLateral _parent;
    private final RotinaTablePanel _rotina_table_panel;

    /**
     * Creates new form Imagens
     *
     * @param parent_window
     * @param rotina_table_panel
     */
    public Ombro(RotinaMenuLateral parent_window, RotinaTablePanel rotina_table_panel) {
        _parent = parent_window;
        _rotina_table_panel = rotina_table_panel;

        initComponents();
        botaoVoltar.addActionListener((e) -> {
            _parent.showHomePage();
            filtro_pesquisa.setText("");
            adicionarBotoes(lista_exercicios);
        });

        adicionarBotoes(lista_exercicios);

        filtro_pesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarLista(filtro_pesquisa.getText());
            }
        });

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

        botaoVoltar.setText("Voltar");

        botaoPesquisar.setText("Pesquisar");

        jPanel1.setLayout(new java.awt.GridLayout(5, 0));
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoPesquisar;
    private javax.swing.JButton botaoVoltar;
    private javax.swing.JTextField filtro_pesquisa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private void adicionarBotoes(List<Exercicio> exercicios) {
        jPanel1.removeAll();

        for (Exercicio exercicio : exercicios) {
            JButton add_novo = new JButton(exercicio.getNomeExercicio());
            add_novo.addActionListener((e) -> {
                _rotina_table_panel.adicionar_exercicio(exercicio);
            });
            jPanel1.add(add_novo);
            jPanel1.revalidate();
            jPanel1.repaint();
        }
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

}
