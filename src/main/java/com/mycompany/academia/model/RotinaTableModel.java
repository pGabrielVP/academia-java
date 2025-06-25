/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.model;

import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.relatorio.ExercicioWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class RotinaTableModel extends AbstractTableModel {

    private final String[] nome_colunas = {"Exercicio", "Reps", "Sets", "Descanço", "Superset"};
    private final ArrayList<ExercicioWrapper> exercicios;
    private final HashMap<Exercicio, Exercicio> superset;

    public RotinaTableModel() {
        this.exercicios = new ArrayList<>();
        this.superset = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        return exercicios.size();
    }

    @Override
    public int getColumnCount() {
        return nome_colunas.length;
    }
 
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExercicioWrapper exercicio_wrapper = getExercicioWrapper(rowIndex);
        
        switch (columnIndex){
            case 0: return exercicio_wrapper.getExercicio().getNomeExercicio();
            case 1: return exercicio_wrapper.getReps();
            case 2: return exercicio_wrapper.getSets();
            case 3: return exercicio_wrapper.getDescanco();
            case 4: return superset.get(exercicio_wrapper.getExercicio());
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ExercicioWrapper exercicioWrapper = getExercicioWrapper(rowIndex);
        
        switch(columnIndex) {
            case 0: exercicioWrapper.setExercicio((Exercicio)aValue); break;
            case 1: exercicioWrapper.setReps((int)aValue); break;
            case 2: exercicioWrapper.setSets((int)aValue); break;
            case 3: exercicioWrapper.setDescanco((int)aValue); break;
            case 4: superset.put(exercicioWrapper.getExercicio(), (Exercicio)aValue); break;
        }
        
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    @Override
    public String getColumnName(int column) {
        return nome_colunas[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: return Exercicio.class;
            case 4: return Exercicio.class;
            default: return Integer.class;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: return false;
            default: return true;
        }
    }

    private ExercicioWrapper getExercicioWrapper(int rowIndex) {
        return exercicios.get(rowIndex);
    }

    public void addExercicioWrapper(ExercicioWrapper exercicioWrapper){
        insertExercicioWrapper(getRowCount(), exercicioWrapper);
    }

    public void insertExercicioWrapper(int row, ExercicioWrapper exercicioWrapper) {
        exercicios.add(row, exercicioWrapper);
        fireTableRowsInserted(row, row);
    }
    
    public void removeExercicioWrapper(int row){
        exercicios.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
}
