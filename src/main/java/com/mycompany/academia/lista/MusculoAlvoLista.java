/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.academia.lista;

import com.mycompany.academia.model.MusculoAlvoModel;
import com.mycompany.academia.controle.MusculoAlvoJpaController;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author paulo
 */
public class MusculoAlvoLista extends javax.swing.JInternalFrame {

    private final MusculoAlvoJpaController controller;
    private final MusculoAlvoModel model;

    /**
     * Creates new form MusculoAlvoLista
     */
    public MusculoAlvoLista() {
        controller = new MusculoAlvoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
        model = new MusculoAlvoModel(controller.findMusculoAlvoEntities());
        initComponents();

        adicionar_novo_musculo_alvo.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Ação não permitida!");
        });

        deletar_musculo_alvo.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Ação não permitida!");
        });

        editar_musuculo_alvo.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Ação não permitida!");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_lista_musculo_alvo = new javax.swing.JTable();
        adicionar_novo_musculo_alvo = new javax.swing.JButton();
        deletar_musculo_alvo = new javax.swing.JButton();
        editar_musuculo_alvo = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(800, 480));

        tabela_lista_musculo_alvo.setModel(model);
        jScrollPane1.setViewportView(tabela_lista_musculo_alvo);

        adicionar_novo_musculo_alvo.setText("Criar");

        deletar_musculo_alvo.setText("Deletar");

        editar_musuculo_alvo.setText("Editar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adicionar_novo_musculo_alvo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deletar_musculo_alvo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editar_musuculo_alvo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(adicionar_novo_musculo_alvo)
                .addGap(18, 18, 18)
                .addComponent(deletar_musculo_alvo)
                .addGap(18, 18, 18)
                .addComponent(editar_musuculo_alvo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionar_novo_musculo_alvo;
    private javax.swing.JButton deletar_musculo_alvo;
    private javax.swing.JButton editar_musuculo_alvo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_lista_musculo_alvo;
    // End of variables declaration//GEN-END:variables
}
