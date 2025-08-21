/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercicio;

import com.mycompany.academia.model.entidades.Exercicio;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class ExercicioTableModel extends AbstractTableModel {

    private final String[] nomeColunas = {"ID", "Nome", "Imagem", "Músculo-Alvo"};
    private final List<Exercicio> listaExercicios; // Remover final? ln:84

    public ExercicioTableModel() {
        listaExercicios = new ArrayList<>();
    }

    public ExercicioTableModel(List<Exercicio> listaExercicios) {
        this.listaExercicios = listaExercicios;
    }

    @Override
    public int getRowCount() {
        return listaExercicios.size();
    }

    @Override
    public int getColumnCount() {
        return nomeColunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return listaExercicios.get(rowIndex).getIdExercicio();
            case 1:
                return listaExercicios.get(rowIndex).getNomeExercicio();
            case 2:
                return listaExercicios.get(rowIndex).getImagem();
            case 3:
                return listaExercicios.get(rowIndex).getMusculoAlvo().getNomeAlvo();
            default:
                throw new Error("columnIndex value not in range");
        }
    }

    @Override
    public String getColumnName(int column) {
        return nomeColunas[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public void deletar(Exercicio exercicio) {
        int linha = listaExercicios.indexOf(exercicio);
        if (linha != -1) {
            deletar(linha);
        }
    }

    private void deletar(int linhaSelecionada) {
        listaExercicios.remove(linhaSelecionada);
        fireTableDataChanged();
    }

    public void oferecer(Exercicio exercicio) {
        int linha = listaExercicios.indexOf(exercicio);
        if (linha == -1) {
            adicionarNovo(exercicio);
        } else {
            atualizar(exercicio);
        }
    }

    private void adicionarNovo(Exercicio exercicio) {
        listaExercicios.add(exercicio);
        fireTableDataChanged();
    }

    private void atualizar(Exercicio exercicio) {
        int linha = listaExercicios.indexOf(exercicio);
        listaExercicios.set(linha, exercicio);
        fireTableDataChanged();
    }

    public void sincronizar(Collection<Exercicio> listaExercicios) {
        this.listaExercicios.clear();
        this.listaExercicios.addAll(listaExercicios); // Remover esse addAll()
        fireTableDataChanged();
    }
}
