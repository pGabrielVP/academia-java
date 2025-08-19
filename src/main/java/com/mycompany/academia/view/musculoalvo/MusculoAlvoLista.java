/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.musculoalvo;

import com.mycompany.academia.controle.MusculoAlvoControle;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

/**
 *
 * @author paulo
 */
public class MusculoAlvoLista extends JFrame {

    private final ListaTable listaTable;
    private final MenuOperacoes menuOperacoes;
    private final BorderLayout borderLayout;
    private final MusculoAlvoControle musculoAlvoControle;

    public MusculoAlvoLista(JFrame parentWindow, MusculoAlvoControle musculoAlvoControle) throws HeadlessException {
        this.musculoAlvoControle = musculoAlvoControle;
        borderLayout = new BorderLayout();
        listaTable = new ListaTable();
        menuOperacoes = new MenuOperacoes();
        setLayout(borderLayout);

        add(listaTable, BorderLayout.CENTER);
        add(menuOperacoes, BorderLayout.EAST);
        pack();
        setTitle("Lista Músculo-Alvo");
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private void criar() {
        new MusculoAlvoEdita(this, musculoAlvoControle, new MusculoAlvo()).setVisible(true);
    }

    private void editar() {
        MusculoAlvo selecao = listaTable.getSelecao();
        if (selecao != null) {
            new MusculoAlvoEdita(this, musculoAlvoControle, selecao).setVisible(true);
        }
    }

    private void deletar() {
        MusculoAlvo selecao = listaTable.getSelecao();
        if (selecao != null) {
            StringBuilder stringBuilder = new StringBuilder()
                    .append("O Músculo-Alvo: ")
                    .append(selecao.getNomeAlvo())
                    .append(" com ID: ")
                    .append(selecao.getIdAlvo())
                    .append(" será removido");
            int confirmarDeletar = JOptionPane.showConfirmDialog(this, stringBuilder.toString(), "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (confirmarDeletar == 0) {
                musculoAlvoControle.excluir(selecao);
            }
        }
    }

    private class ListaTable extends JScrollPane {

        private final JTable jTable;

        public ListaTable() {
            jTable = new JTable(MusculoAlvoLista.this.musculoAlvoControle.getMusculoAlvoTableModel());
            jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jTable.getColumnModel().getColumn(0).setMaxWidth(50); // Reserva menos espaço para a coluna de ID.
            MusculoAlvoLista.this.musculoAlvoControle.sincronizarMusculoAlvoTableModel();
            setViewportView(jTable);
        }

        private MusculoAlvo getSelecao() {
            int linhaSelecionada = jTable.getSelectedRow();
            if (linhaSelecionada == -1) {
                return null;
            }
            int idMusculoAlvo = (Integer) jTable.getValueAt(linhaSelecionada, 0);
            MusculoAlvo musculoAlvoSelecionado = MusculoAlvoLista.this.musculoAlvoControle.buscar(idMusculoAlvo);
            return musculoAlvoSelecionado;
        }
    }

    private class MenuOperacoes extends JPanel {

        private final BoxLayout boxLayout;
        private JButton criar;
        private JButton deletar;
        private JButton editar;

        public MenuOperacoes() {
            boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            setLayout(boxLayout);
            initComponents();
        }

        private void initComponents() {
            float alignment = 0.7f;
            Dimension minimum = new Dimension(0, 0);
            Dimension buttonGap = new Dimension(0, 20);
            Dimension topGap = new Dimension(60, 30);

            criar = new JButton("Criar");
            criar.setAlignmentX(alignment - 0.07f);
            criar.addActionListener((e) -> {
                requestCriar();
            });

            deletar = new JButton("Deletar");
            deletar.setAlignmentX(alignment);
            deletar.addActionListener((e) -> {
                requestDeletar();
            });

            editar = new JButton("Editar");
            editar.setAlignmentX(alignment - 0.03f);
            editar.addActionListener((e) -> {
                requestEditar();
            });

            add(new Box.Filler(minimum, topGap, topGap));
            add(criar);
            add(new Box.Filler(minimum, buttonGap, buttonGap));
            add(deletar);
            add(new Box.Filler(minimum, buttonGap, buttonGap));
            add(editar);
        }

        private void requestCriar() {
            MusculoAlvoLista.this.criar();
        }

        private void requestDeletar() {
            MusculoAlvoLista.this.deletar();
        }

        private void requestEditar() {
            MusculoAlvoLista.this.editar();
        }

    }
}
