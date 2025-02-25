/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.lista;

import com.mycompany.academia.entidades.Equipamento;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class EquipamentoModel extends AbstractTableModel {

    private final String[] nome_colunas = {"id_equipamento", "nome_equipamento"};
    private final List<Equipamento> lista_equipamentos;

    public EquipamentoModel(List<Equipamento> dados) {
        lista_equipamentos = dados;
    }

    @Override
    public int getRowCount() {
        return lista_equipamentos.size();
    }

    @Override
    public int getColumnCount() {
        return nome_colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return lista_equipamentos.get(rowIndex).getIdEquipamento();
            case 1:
                return lista_equipamentos.get(rowIndex).getNomeEquipamento();
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

    public void adicionarNovo(Equipamento e) {
        lista_equipamentos.add(e);
        this.fireTableDataChanged();
    }

    public void deletar(int linha_selecionada) {
        lista_equipamentos.remove(linha_selecionada);
        this.fireTableDataChanged();
    }

    public void atualizar(Equipamento e) {
        int linha = lista_equipamentos.indexOf(e);
        lista_equipamentos.set(linha, e);
        this.fireTableDataChanged();
    }
}
