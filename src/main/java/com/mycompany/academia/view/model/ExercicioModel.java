/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.model;

import com.mycompany.academia.model.entidades.Exercicio;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class ExercicioModel extends AbstractTableModel {

    private final String[] nome_colunas = {"exercicio_id", "nome_exercicio", "imagem", "musculo_alvo"};
    private final List<Exercicio> lista_exercicios;

    public ExercicioModel(List<Exercicio> dados) {
        lista_exercicios = dados;
    }

    @Override
    public int getRowCount() {
        return lista_exercicios.size();
    }

    @Override
    public int getColumnCount() {
        return nome_colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return lista_exercicios.get(rowIndex).getIdExercicio();
            case 1:
                return lista_exercicios.get(rowIndex).getNomeExercicio();
            case 2:
                return lista_exercicios.get(rowIndex).getImagem();
            case 3:
                return lista_exercicios.get(rowIndex).getMusculoAlvo().getNomeAlvo();
            default:
                throw new Error("columnIndex value not in range");
        }
    }

    @Override
    public String getColumnName(int column) {
        return nome_colunas[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public void adicionarNovo(Exercicio e) {
        lista_exercicios.add(e);
        this.fireTableDataChanged();
    }

    public void deletar(int linha_selecionada) {
        lista_exercicios.remove(linha_selecionada);
        this.fireTableDataChanged();
    }

    public void atualizar(Exercicio e) {
        int linha = lista_exercicios.indexOf(e);
        lista_exercicios.set(linha, e);
        this.fireTableDataChanged();
    }
}
