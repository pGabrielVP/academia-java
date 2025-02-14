/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.datamodel;

import com.mycompany.academia.controle.ImagemKeywordJpaController;
import com.mycompany.academia.entidades.ImagemKeyword;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class TableModelImagemKeyword extends AbstractTableModel {

    String[] columnNames = {"Id", "Imagem", "Keyword"};

    ImagemKeywordJpaController imagemKeywordJpaController = new ImagemKeywordJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    List<ImagemKeyword> listaImagemKeyword = imagemKeywordJpaController.findImagemKeywordEntities();

    @Override
    public int getRowCount() {
        // numero de entidades no banco de dados.
        return imagemKeywordJpaController.getImagemKeywordCount();
    }

    @Override
    public int getColumnCount() {
        // numero de colunas na tabela.
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // adiciona as entradas da lista na tabela.
        if (columnIndex == 0) {
            return listaImagemKeyword.get(rowIndex).getId();
        } else if (columnIndex == 1) {
            ImageIcon imageIcon = new ImageIcon(listaImagemKeyword.get(rowIndex).getImagem().getImagem());
            return imageIcon;
        } else if (columnIndex == 2) {
            return listaImagemKeyword.get(rowIndex).getKeyword();
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
