/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.muscle;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.TargetMuscleController;
import com.mycompany.academia.model.entity.MusculoAlvo;

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
import com.mycompany.academia.view.ListTableOperation;

/**
 *
 * @author paulo
 */
public final class TargetMuscleList extends JFrame {

    private final MusculoAlvoListaPanel musculoAlvoListaPanel;

    public TargetMuscleList(JFrame parentWindow, TargetMuscleController musculoAlvoControle) throws HeadlessException {
        musculoAlvoListaPanel = new MusculoAlvoListaPanel(this, musculoAlvoControle);
        setContentPane(musculoAlvoListaPanel);
        pack();
        setTitle(LocalizationManager.getString("localization.Strings", "muscle.list.window.title"));
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private class MusculoAlvoListaPanel extends JPanel implements ListTableOperation {

        private final JFrame parentWindow;
        private final BorderLayout borderLayout;
        private final TargetMuscleController musculoAlvoControle;
        private final ListaTable listaTable;
        private final MenuOperacoes menuOperacoes;

        public MusculoAlvoListaPanel(JFrame parentWindow, TargetMuscleController musculoAlvoControle) {
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
            new TargetMuscleEdit(parentWindow, musculoAlvoControle, new MusculoAlvo()).setVisible(true);
        }

        @Override
        public void editar() {
            MusculoAlvo selecao = listaTable.getSelecao();
            if (selecao != null) {
                new TargetMuscleEdit(parentWindow, musculoAlvoControle, selecao).setVisible(true);
            }
        }

        @Override
        public void deletar() {
            MusculoAlvo selecao = listaTable.getSelecao();
            if (selecao != null) {
                var deleteWarning = LocalizationManager.getFormattedString("localization.Strings",
                        "muscle.list.delete.warning.message", selecao.getNomeAlvo(), selecao.getIdAlvo());
                var buttonConfirm = LocalizationManager.getString("localization.Strings",
                        "muscle.list.delete.confirmation.message");
                int confirmarDeletar = JOptionPane.showConfirmDialog(this, deleteWarning, buttonConfirm,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (confirmarDeletar == JOptionPane.OK_OPTION) {
                    musculoAlvoControle.excluir(selecao);
                }
            }
        }

        private class ListaTable extends JScrollPane {

            private final JTable jTable;
            private final TargetMuscleController musculoAlvoControle;

            public ListaTable(TargetMuscleController musculoAlvoControle) {
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

            private final ListTableOperation parentWindow;
            private final BoxLayout boxLayout;
            private JButton criar;
            private JButton deletar;
            private JButton editar;

            public MenuOperacoes(ListTableOperation parentWindow) {
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

                criar = new JButton(LocalizationManager.getString("localization.Strings", "muscle.list.button.create"));
                criar.setAlignmentX(alignment - 0.07f);
                criar.addActionListener((e) -> {
                    requestCriar();
                });

                deletar = new JButton(
                        LocalizationManager.getString("localization.Strings", "muscle.list.button.delete"));
                deletar.setAlignmentX(alignment);
                deletar.addActionListener((e) -> {
                    requestDeletar();
                });

                editar = new JButton(LocalizationManager.getString("localization.Strings", "muscle.list.button.edit"));
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
