/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.rotina;

import com.mycompany.academia.controle.RotinaControle;
import com.mycompany.academia.model.dto.ExercicioWrapper;
import com.mycompany.academia.model.entidades.Exercicio;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author paulo
 */
public final class RotinaConteudo extends JPanel {

    private final RotinaControle rotinaControle;
    private int janelasAdicionadas;

    private JTabbedPane guias;
    private JPanel operacoes;
    private JButton resetar;
    private JButton adicionarGuia;
    private JButton removerExercicio;
    private JButton removerGuia;
    private JButton imprimir;

    public RotinaConteudo(RotinaControle rotinaControle) {
        this.rotinaControle = rotinaControle;
        janelasAdicionadas = 0;
        initComponents();
        adicionarGuia();
    }

    public void adicionarExercicio(Exercicio exercicio) {
        ExercicioWrapper novoExercicio = new ExercicioWrapper(exercicio, 12, 3, 30);
        JScrollPane guiaAtual = (JScrollPane) guias.getSelectedComponent();
        JTable table = (JTable) guiaAtual.getViewport().getView();
        RotinaTableModel tableModel = (RotinaTableModel) table.getModel();
        tableModel.addExercicioWrapper(novoExercicio);
        atualizarComboBox(table);
    }

    private void atualizarComboBox(JTable table) {
        JComboBox<Exercicio> comboBox = new JComboBox<>();

        int linhas = table.getModel().getRowCount();
        for (int i = 0; i < linhas; i++) {
            Exercicio exercicio = (Exercicio) table.getModel().getValueAt(i, 0);
            comboBox.addItem(exercicio);
        }
        comboBox.addItem(null);

        TableColumn colunaSuperset = table.getColumnModel().getColumn(4);
        colunaSuperset.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void adicionarGuia() {
        JTable table = new JTable(new RotinaTableModel());
        atualizarComboBox(table);
        String letra = Character.toString(65 + janelasAdicionadas++);
        guias.insertTab(letra, null, new JScrollPane(table), "", guias.getTabCount());
    }

    private void removerExercicio() {
        JScrollPane guiaAtual = (JScrollPane) guias.getSelectedComponent();
        JTable table = (JTable) guiaAtual.getViewport().getView();
        RotinaTableModel tableModel = (RotinaTableModel) table.getModel();
        int selected_row = table.getSelectedRow();
        if (selected_row < 0) {
            JOptionPane.showMessageDialog(this, "Nenhum exercício selecionado");
            return;
        }
        tableModel.removeExercicioWrapper(selected_row);
        atualizarComboBox(table);
    }

    private void removerGuia() {
        if (guias.getTabCount() == 1) {
            return;
        }
        int selected_tab = guias.getSelectedIndex();
        guias.remove(selected_tab);
    }

    private void resetar() {
        guias.removeAll();
        janelasAdicionadas = 0;
        adicionarGuia();
    }

    private void imprimir() {
        ArrayList<String> listaTitulos = getTitulos();
        ArrayList<ArrayList<ExercicioWrapper>> listaExercicios = getListaExercicios();
        ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> listaSuperset = getListaSuperset();

        rotinaControle.imprimirRelatorio(listaTitulos, listaExercicios, listaSuperset);
    }

    private ArrayList<String> getTitulos() {
        ArrayList<String> listaTitulos = new ArrayList<>();
        for (int i = 0; i < guias.getTabCount(); i++) {
            String titulo = guias.getTitleAt(i);
            listaTitulos.add(titulo);
        }
        return listaTitulos;
    }

    private ArrayList<ArrayList<ExercicioWrapper>> getListaExercicios() {
        ArrayList<ArrayList<ExercicioWrapper>> listaExercicios = new ArrayList<>();
        for (int i = 0; i < guias.getTabCount(); i++) {
            JScrollPane guiaAtual = (JScrollPane) guias.getComponentAt(i);
            JTable table = (JTable) guiaAtual.getViewport().getView();
            RotinaTableModel tableModel = (RotinaTableModel) table.getModel();
            ArrayList<ExercicioWrapper> exercicios = tableModel.getExercicios();
            listaExercicios.add(exercicios);
        }
        return listaExercicios;
    }

    private ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> getListaSuperset() {
        ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> listaSuperset = new ArrayList<>();
        for (int i = 0; i < guias.getTabCount(); i++) {
            JScrollPane guiaAtual = (JScrollPane) guias.getComponentAt(i);
            JTable table = (JTable) guiaAtual.getViewport().getView();
            RotinaTableModel tableModel = (RotinaTableModel) table.getModel();
            HashMap<ExercicioWrapper, ExercicioWrapper> superset = tableModel.getSuperset();
            listaSuperset.add(superset);
        }
        return listaSuperset;
    }

    private void initComponents() {
        guias = new JTabbedPane();
        operacoes = new JPanel();

        float alignment = 0.7f;
        Dimension minimum = new Dimension(0, 0);
        Dimension buttonGap = new Dimension(0, 20);
        Dimension topGap = new Dimension(60, 30);

        resetar = new JButton("Resetar");
        resetar.setAlignmentX(alignment - 0.23f);
        resetar.setBackground(new Color(204, 0, 0));
        resetar.setForeground(new Color(255, 255, 255));
        resetar.addActionListener((e) -> {
            resetar();
        });

        adicionarGuia = new JButton("Adicionar Guia");
        adicionarGuia.setAlignmentX(alignment - 0.07f);
        adicionarGuia.addActionListener((e) -> {
            adicionarGuia();
        });

        removerExercicio = new JButton("Remover Exercício");
        removerExercicio.setAlignmentX(alignment);
        removerExercicio.addActionListener((e) -> {
            removerExercicio();
        });

        removerGuia = new JButton("Remover Guia");
        removerGuia.setAlignmentX(alignment - 0.08f);
        removerGuia.addActionListener((e) -> {
            removerGuia();
        });

        imprimir = new JButton("Imprimir");
        imprimir.setAlignmentX(alignment - 0.22f);
        imprimir.addActionListener((e) -> {
            imprimir();
        });

        BoxLayout boxLayout = new BoxLayout(operacoes, BoxLayout.Y_AXIS);
        operacoes.setLayout(boxLayout);
        operacoes.add(new Box.Filler(minimum, topGap, topGap));
        operacoes.add(resetar);
        operacoes.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operacoes.add(adicionarGuia);
        operacoes.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operacoes.add(removerExercicio);
        operacoes.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operacoes.add(removerGuia);
        operacoes.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operacoes.add(imprimir);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(819, 551));
        add(guias, BorderLayout.CENTER);
        add(operacoes, BorderLayout.EAST);
    }

}
