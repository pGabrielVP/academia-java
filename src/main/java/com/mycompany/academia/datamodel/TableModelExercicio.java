/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.datamodel;

import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.entidades.Exercicio;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class TableModelExercicio extends AbstractTableModel {

    // nome das colunas
    String[] columnNames = {"Id", "Nome", "Movimento"};

    // conexão com o banco
    // lista com o valores recuperados
    ExercicioJpaController exercicioJpaController = new ExercicioJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    List<Exercicio> listaImagens = exercicioJpaController.findExercicioEntities();

    @Override
    public int getRowCount() {
        // numero de entidades no banco de dados.
        return exercicioJpaController.getExercicioCount();
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
            return listaImagens.get(rowIndex).getNome();
        } else if (columnIndex == 2) {
            return listaImagens.get(rowIndex).getMovimento();
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
