/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.musculoalvo;

import com.mycompany.academia.model.entidades.MusculoAlvo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class MusculoAlvoTableModel extends AbstractTableModel {

    private final String[] nomeColunas = {"ID", "Músculo-Alvo"};
    private final List<MusculoAlvo> listaMusculoAlvo; // Remover final? ln:84

    public MusculoAlvoTableModel() {
        listaMusculoAlvo = new ArrayList<>();
    }

    public MusculoAlvoTableModel(List<MusculoAlvo> listaMusculoAlvo) {
        this.listaMusculoAlvo = listaMusculoAlvo;
    }

    @Override
    public int getRowCount() {
        return listaMusculoAlvo.size();
    }

    @Override
    public int getColumnCount() {
        return nomeColunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return listaMusculoAlvo.get(rowIndex).getIdAlvo();
            case 1:
                return listaMusculoAlvo.get(rowIndex).getNomeAlvo();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return nomeColunas[column];
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public void deletar(int linhaSelecionada) {
        listaMusculoAlvo.remove(linhaSelecionada);
        fireTableDataChanged();
    }

    public void deletar(MusculoAlvo musculoAlvo) {
        int linha = listaMusculoAlvo.indexOf(musculoAlvo);
        if (linha != -1) {
            deletar(linha);
        }
    }

    private void adicionarNovo(MusculoAlvo musculoAlvo) {
        listaMusculoAlvo.add(musculoAlvo);
        fireTableDataChanged();
    }

    private void atualizar(MusculoAlvo musculoAlvo) {
        int linha = listaMusculoAlvo.indexOf(musculoAlvo);
        listaMusculoAlvo.set(linha, musculoAlvo);
        fireTableDataChanged();
    }

    public void oferecer(MusculoAlvo musculoAlvo) {
        int linha = listaMusculoAlvo.indexOf(musculoAlvo);
        if (linha == -1) {
            adicionarNovo(musculoAlvo);
        } else {
            atualizar(musculoAlvo);
        }
    }

    public void sincronizar(Collection<MusculoAlvo> listaMusculoAlvo) {
        this.listaMusculoAlvo.clear();
        this.listaMusculoAlvo.addAll(listaMusculoAlvo); // Remover esse addAll()
        fireTableDataChanged();
    }
}
