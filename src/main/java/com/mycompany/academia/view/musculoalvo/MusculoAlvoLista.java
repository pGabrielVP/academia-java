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
import com.mycompany.academia.view.ListaTableOperacoes;

/**
 *
 * @author paulo
 */
public final class MusculoAlvoLista extends JFrame {

    private final MusculoAlvoListaPanel musculoAlvoListaPanel;

    public MusculoAlvoLista(JFrame parentWindow, MusculoAlvoControle musculoAlvoControle) throws HeadlessException {
        musculoAlvoListaPanel = new MusculoAlvoListaPanel(this, musculoAlvoControle);
        setContentPane(musculoAlvoListaPanel);
        pack();
        setTitle("Lista Músculo-Alvo");
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private class MusculoAlvoListaPanel extends JPanel implements ListaTableOperacoes {

        private final JFrame parentWindow;
        private final BorderLayout borderLayout;
        private final MusculoAlvoControle musculoAlvoControle;
        private final ListaTable listaTable;
        private final MenuOperacoes menuOperacoes;

        public MusculoAlvoListaPanel(JFrame parentWindow, MusculoAlvoControle musculoAlvoControle) {
            this.parentWindow = parentWindow;
            this.musculoAlvoControle = musculoAlvoControle;
            borderLayout = new BorderLayout();
            listaTable = new ListaTable(musculoAlvoControle);
            menuOperacoes = new MenuOperacoes(this);
            setLayout(borderLayout);
            add(listaTable, BorderLayout.CENTER);
            add(menuOperacoes, BorderLayout.EAST);
        }

        @Override
        public void criar() {
            new MusculoAlvoEdita(parentWindow, musculoAlvoControle, new MusculoAlvo()).setVisible(true);
        }

        @Override
        public void editar() {
            MusculoAlvo selecao = listaTable.getSelecao();
            if (selecao != null) {
                new MusculoAlvoEdita(parentWindow, musculoAlvoControle, selecao).setVisible(true);
            }
        }

        @Override
        public void deletar() {
            MusculoAlvo selecao = listaTable.getSelecao();
            if (selecao != null) {
                String mensagem = "O Músculo-Alvo: " + selecao.getNomeAlvo() + " com ID: " + selecao.getIdAlvo() + " será removido";
                int confirmarDeletar = JOptionPane.showConfirmDialog(this, mensagem, "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (confirmarDeletar == JOptionPane.OK_OPTION) {
                    musculoAlvoControle.excluir(selecao);
                }
            }
        }

        private class ListaTable extends JScrollPane {

            private final JTable jTable;
            private final MusculoAlvoControle musculoAlvoControle;

            public ListaTable(MusculoAlvoControle musculoAlvoControle) {
                this.musculoAlvoControle = musculoAlvoControle;
                jTable = new JTable(musculoAlvoControle.getMusculoAlvoTableModel());
                jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable.getColumnModel().getColumn(0).setMaxWidth(50); // Reserva menos espaço para a coluna de ID.
                setViewportView(jTable);
            }

            private MusculoAlvo getSelecao() {
                int linhaSelecionada = jTable.getSelectedRow();
                if (linhaSelecionada == -1) {
                    return null;
                }
                int idMusculoAlvo = (Integer) jTable.getValueAt(linhaSelecionada, 0);
                MusculoAlvo musculoAlvoSelecionado = musculoAlvoControle.buscar(idMusculoAlvo);
                return musculoAlvoSelecionado;
            }
        }

        private class MenuOperacoes extends JPanel {

            private final ListaTableOperacoes parentWindow;
            private final BoxLayout boxLayout;
            private JButton criar;
            private JButton deletar;
            private JButton editar;

            public MenuOperacoes(ListaTableOperacoes parentWindow) {
                this.parentWindow = parentWindow;
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
                parentWindow.criar();
            }

            private void requestDeletar() {
                parentWindow.deletar();
            }

            private void requestEditar() {
                parentWindow.editar();
            }

        }

    }

}
