/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercise;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.model.entity.Exercicio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class ExerciseTableModel extends AbstractTableModel {

    private final String id = LocalizationManager.getString("localization.Strings",
            "exercise.table.model.column.name.id");
    private final String name = LocalizationManager.getString("localization.Strings",
            "exercise.table.model.column.name.name");
    private final String image = LocalizationManager.getString("localization.Strings",
            "exercise.table.model.column.name.image");
    private final String muscle = LocalizationManager.getString("localization.Strings",
            "exercise.table.model.column.name.muscle");
    private final String[] columnNames = { id, name, image, muscle };
    private final List<Exercicio> exerciseList; // Remover final? ln:84

    public ExerciseTableModel() {
        exerciseList = new ArrayList<>();
    }

    public ExerciseTableModel(List<Exercicio> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public int getRowCount() {
        return exerciseList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return exerciseList.get(rowIndex).getIdExercicio();
            case 1:
                return exerciseList.get(rowIndex).getNomeExercicio();
            case 2:
                return exerciseList.get(rowIndex).getImagem();
            case 3:
                return exerciseList.get(rowIndex).getMusculoAlvo().getNomeAlvo();
            default:
                throw new Error("columnIndex value not in range");
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

    public void delete(Exercicio exercise) {
        int linha = exerciseList.indexOf(exercise);
        if (linha != -1) {
            delete(linha);
        }
    }

    private void delete(int selectedLine) {
        exerciseList.remove(selectedLine);
        fireTableDataChanged();
    }

    public void offer(Exercicio exercise) {
        int linha = exerciseList.indexOf(exercise);
        if (linha == -1) {
            addNew(exercise);
        } else {
            update(exercise);
        }
    }

    private void addNew(Exercicio exercise) {
        exerciseList.add(exercise);
        fireTableDataChanged();
    }

    private void update(Exercicio exercise) {
        int lineNumber = exerciseList.indexOf(exercise);
        exerciseList.set(lineNumber, exercise);
        fireTableDataChanged();
    }

    public void sync(Collection<Exercicio> exerciseList) {
        this.exerciseList.clear();
        this.exerciseList.addAll(exerciseList); // Remover esse addAll()
        fireTableDataChanged();
    }
}
