/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.datamodel;

import com.mycompany.academia.controle.KeywordsJpaController;
import com.mycompany.academia.entidades.Keywords;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class TableModelKeyword extends AbstractTableModel {

    String[] columnNames = {"Id", "Keyword"};

    KeywordsJpaController keywordsJpaController = new KeywordsJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    List<Keywords> listaKeywords = keywordsJpaController.findKeywordsEntities();

    @Override
    public int getRowCount() {
        // numero de entidades no banco de dados.
        return keywordsJpaController.getKeywordsCount();
    }

    @Override
    public int getColumnCount() {
        // numero de colunas na tabela.
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // adiciona as entradas do banco de dados na tabela.
        if (columnIndex == 0) {
            return listaKeywords.get(rowIndex).getId();
        } else if (columnIndex == 1) {
            return listaKeywords.get(rowIndex).getKeyword();
        }
        throw new Error("columnIndex value not in range");
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
