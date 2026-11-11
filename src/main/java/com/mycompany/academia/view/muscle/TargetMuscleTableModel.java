/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.muscle;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.model.entity.MusculoAlvo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class TargetMuscleTableModel extends AbstractTableModel {

    private final String id = LocalizationManager.getString("localization.Strings", "muscle.table.model.column.name.id");
    private final String muscle = LocalizationManager.getString("localization.Strings", "muscle.table.model.column.name.name");
    private final String[] columnNames = {id, muscle};
    private final List<MusculoAlvo> targetMuscleList; // Remover final? ln:84

    public TargetMuscleTableModel() {
        targetMuscleList = new ArrayList<>();
    }

    public TargetMuscleTableModel(List<MusculoAlvo> targetMuscleList) {
        this.targetMuscleList = targetMuscleList;
    }

    @Override
    public int getRowCount() {
        return targetMuscleList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return targetMuscleList.get(rowIndex).getIdAlvo();
            case 1:
                return targetMuscleList.get(rowIndex).getNomeAlvo();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public void delete(MusculoAlvo targetMuscle) {
        int linha = targetMuscleList.indexOf(targetMuscle);
        if (linha != -1) {
            delete(linha);
        }
    }

    private void delete(int selectedLineNumber) {
        targetMuscleList.remove(selectedLineNumber);
        fireTableDataChanged();
    }

    public void offer(MusculoAlvo targetMuscle) {
        int lineNumber = targetMuscleList.indexOf(targetMuscle);
        if (lineNumber == -1) {
            addNew(targetMuscle);
        } else {
            update(targetMuscle);
        }
    }

    private void addNew(MusculoAlvo targetMuscle) {
        targetMuscleList.add(targetMuscle);
        fireTableDataChanged();
    }

    private void update(MusculoAlvo targetMuscle) {
        int lineNumber = targetMuscleList.indexOf(targetMuscle);
        targetMuscleList.set(lineNumber, targetMuscle);
        fireTableDataChanged();
    }

    public void sync(Collection<MusculoAlvo> targetMuscleList) {
        this.targetMuscleList.clear();
        this.targetMuscleList.addAll(targetMuscleList); // Remover esse addAll()
        fireTableDataChanged();
    }
}
