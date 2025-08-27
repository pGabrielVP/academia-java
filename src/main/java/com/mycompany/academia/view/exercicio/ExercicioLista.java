/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercicio;

import com.mycompany.academia.controle.ExercicioControle;
import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.view.ListaTableOperacoes;
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
public final class ExercicioLista extends JFrame {

    private final ExercicioListaPanel exercicioListaPanel;

    public ExercicioLista(JFrame parentWindow, ExercicioControle exercicioControle) throws HeadlessException {
        exercicioListaPanel = new ExercicioListaPanel(this, exercicioControle);
        setContentPane(exercicioListaPanel);
        pack();
        setTitle("Lista Exercícios");
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private class ExercicioListaPanel extends JPanel implements ListaTableOperacoes {

        private final JFrame parentWindow;
        private final BorderLayout borderLayout;
        private final ExercicioControle exercicioControle;
        private final ListaTable listaTable;
        private final MenuOperacoes menuOperacoes;

        public ExercicioListaPanel(JFrame parentWindow, ExercicioControle exercicioControle) {
            this.parentWindow = parentWindow;
            this.exercicioControle = exercicioControle;
            borderLayout = new BorderLayout();
            listaTable = new ListaTable(exercicioControle);
            menuOperacoes = new MenuOperacoes(this);
            setLayout(borderLayout);
            add(listaTable, BorderLayout.CENTER);
            add(menuOperacoes, BorderLayout.EAST);
        }

        @Override
        public void criar() {
            new ExercicioEdita(parentWindow, exercicioControle, new Exercicio()).setVisible(true);
        }

        @Override
        public void editar() {
            Exercicio selecao = listaTable.getSelecao();
            if (selecao != null) {
                new ExercicioEdita(parentWindow, exercicioControle, selecao).setVisible(true);
            }
        }

        @Override
        public void deletar() {
            Exercicio selecao = listaTable.getSelecao();
            if (selecao != null) {
                StringBuilder stringBuilder = new StringBuilder()
                        .append("O Exercício: ")
                        .append(selecao.getNomeExercicio())
                        .append(" com ID: ")
                        .append(selecao.getIdExercicio())
                        .append(" será removido");
                int confirmarDeletar = JOptionPane.showConfirmDialog(this, stringBuilder.toString(), "Confirmar Remoção", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (confirmarDeletar == JOptionPane.OK_OPTION) {
                    exercicioControle.excluir(selecao);
                }
            }
        }

        private class ListaTable extends JScrollPane {

            private final JTable jTable;
            private final ExercicioControle exercicioControle;

            public ListaTable(ExercicioControle exercicioControle) {
                this.exercicioControle = exercicioControle;
                jTable = new JTable(exercicioControle.getExercicioTableModel());
                jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable.getColumnModel().getColumn(0).setMaxWidth(50); // Reserva menos espaço para a coluna de ID.
                setViewportView(jTable);
            }

            private Exercicio getSelecao() {
                int linhaSelecionada = jTable.getSelectedRow();
                if (linhaSelecionada == -1) {
                    return null;
                }
                int idMusculoAlvo = (Integer) jTable.getValueAt(linhaSelecionada, 0);
                Exercicio exercicioSelecionado = exercicioControle.buscar(idMusculoAlvo);
                return exercicioSelecionado;
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
