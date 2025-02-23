/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author paulo
 */
public class RotinaDireita extends javax.swing.JPanel {

    private final List<DefaultListModel<String>> model_lista = new ArrayList<>();
    // painelLista.getTabCount() fazia paineis terem nomes repetidos quando algum era removido
    // essa variavel resolve isso
    private int contagem_paineis = 0;

    /**
     * Creates new form RotinaDireita
     */
    public RotinaDireita() {
        initComponents();
        adicionar_nova_lista.addActionListener((e) -> {
            JPanel container = new JPanel(new BorderLayout());

            JScrollPane painel = new JScrollPane();
            JList<String> lista = new JList<>();
            painel.setViewportView(lista);
            model_lista.add(new DefaultListModel<>());
            lista.setModel(model_lista.get(painel_lista.getTabCount()));

            // Alterar essa ordem quebra o actionListener em deletar_seleção
            // Linha 66; ((JPanel) painel).getComponent(0);
            container.add(painel, BorderLayout.CENTER);
            container.add(detalhes(), BorderLayout.SOUTH);

//            painel_lista.addTab("lista " + (contagem_paineis + 1), painel);
            painel_lista.addTab(Character.toString(65 + contagem_paineis), container);
            contagem_paineis += 1;
        });
        deletar_selecao.addActionListener((e) -> {
            int guia = painel_lista.getSelectedIndex();
            Component painel = painel_lista.getComponentAt(guia);
            Component container = ((JPanel) painel).getComponent(0);
            Component lista = ((JScrollPane) container).getViewport().getView();
            int indice_item_selecionado = ((JList) lista).getSelectedIndex();

            model_lista.get(guia).remove(indice_item_selecionado);
        });
        painel_lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem deletar = new JMenuItem(new AbstractAction("deletar") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int guia = painel_lista.getSelectedIndex();
                            // deleta a guia e o model
                            painel_lista.remove(guia);
                            model_lista.remove(guia);
                        }
                    });
                    JMenuItem renomear = new JMenuItem(new AbstractAction("renomear") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int guia = painel_lista.getSelectedIndex();
                            String novo_nome = JOptionPane.showInputDialog("Novo Nome");
                            if (novo_nome != null && !novo_nome.isBlank()) {
                                painel_lista.setTitleAt(guia, novo_nome);
                            }
                        }
                    });
                    menu.add(renomear);
                    menu.add(deletar);
                    menu.show(painel_lista, e.getX(), e.getY());
                }
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

        painel_lista = new javax.swing.JTabbedPane();
        adicionar_nova_lista = new javax.swing.JButton();
        deletar_selecao = new javax.swing.JButton();
        imprimir = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(819, 551));

        adicionar_nova_lista.setText("Nova Lista");

        deletar_selecao.setText("Remover Exercicio Selecionado");

        imprimir.setText("Imprimir");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel_lista, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adicionar_nova_lista, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deletar_selecao, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imprimir, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_lista, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(adicionar_nova_lista)
                .addGap(18, 18, 18)
                .addComponent(deletar_selecao)
                .addGap(18, 18, 18)
                .addComponent(imprimir)
                .addContainerGap(387, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionar_nova_lista;
    private javax.swing.JButton deletar_selecao;
    private javax.swing.JButton imprimir;
    private javax.swing.JTabbedPane painel_lista;
    // End of variables declaration//GEN-END:variables

    // pensar em um nome melhor
    private JPanel detalhes() {
        JPanel detalhes = new JPanel(new GridLayout(3, 2));
        JLabel reps_label = new JLabel("Repetições: ");
        JLabel sets_label = new JLabel("Sets: ");
        JLabel descanco_label = new JLabel("Descanço: ");
        JSpinner sets = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        JSpinner reps = new JSpinner(new SpinnerNumberModel(12, 1, 1000, 2));
        JSpinner descanco = new JSpinner(new SpinnerNumberModel(30, 0, 300, 5));
        descanco.setToolTipText("Tempo de descanço em segundos");

        detalhes.add(reps_label);
        detalhes.add(reps);
        detalhes.add(sets_label);
        detalhes.add(sets);
        detalhes.add(descanco_label);
        detalhes.add(descanco);

        return detalhes;
    }

    public JTabbedPane getPainel_lista() {
        return painel_lista;
    }

    public List<DefaultListModel<String>> getModel_lista() {
        return model_lista;
    }

}
