/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.datamodel;

import com.mycompany.academia.controle.MovimentoJpaController;
import com.mycompany.academia.entidades.Movimento;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class TableModelMovimento extends AbstractTableModel {

    String[] columnNames = {"Id", "Imagem Antes", "Imagem Depois"};

    MovimentoJpaController movimentoJpaController = new MovimentoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    List<Movimento> listaImagens = movimentoJpaController.findMovimentoEntities();

    @Override
    public int getRowCount() {
        // numero de entidades no banco de dados.
        return movimentoJpaController.getMovimentoCount();
    }

    @Override
    public int getColumnCount() {
        // numero de colunas na tabela.
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // adiciona as entradas do banco de dados na tabela.
        if (columnIndex == 0) {
            return listaImagens.get(rowIndex).getId();

        } else if (columnIndex == 1) {
            ImageIcon imageIcon = new ImageIcon(listaImagens.get(rowIndex).getAntes().getImagem());
            return imageIcon;

        } else if (columnIndex == 2) {
            ImageIcon imageIcon = new ImageIcon(listaImagens.get(rowIndex).getDepois().getImagem());
            return imageIcon;
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
