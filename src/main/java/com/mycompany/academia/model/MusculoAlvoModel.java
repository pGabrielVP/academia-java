/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.model;

import com.mycompany.academia.entidades.MusculoAlvo;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class MusculoAlvoModel extends AbstractTableModel {

    private final String[] nome_colunas = {"id_musculo_alvo", "alvo"};
    private final List<MusculoAlvo> lista_musculo_alvo;

    public MusculoAlvoModel(List<MusculoAlvo> dados) {
        lista_musculo_alvo = dados;
    }

    @Override
    public int getRowCount() {
        return lista_musculo_alvo.size();
    }

    @Override
    public int getColumnCount() {
        return nome_colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return lista_musculo_alvo.get(rowIndex).getIdAlvo();
            case 1:
                return lista_musculo_alvo.get(rowIndex).getNomeAlvo();
            default:
                throw new AssertionError();
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
}
