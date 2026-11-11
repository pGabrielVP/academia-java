/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.routine;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.model.dto.ExercicioWrapper;
import com.mycompany.academia.model.entity.Exercicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author paulo
 */
public class RoutineTableModel extends AbstractTableModel {

    private final String exercise = LocalizationManager.getString("localization.Strings", "routine.table.model.column.name.exercise");
    private final String reps = LocalizationManager.getString("localization.Strings", "routine.table.model.column.name.reps");
    private final String sets = LocalizationManager.getString("localization.Strings", "routine.table.model.column.name.sets");
    private final String rest = LocalizationManager.getString("localization.Strings", "routine.table.model.column.name.rest");
    private final String supersetString = LocalizationManager.getString("localization.Strings", "routine.table.model.column.name.superset");
    private final String[] columnNames = {exercise, reps, sets, rest, supersetString};
    private final ArrayList<ExercicioWrapper> exercises;
    private final HashMap<Exercicio, Exercicio> supersets;

    public RoutineTableModel() {
        this.exercises = new ArrayList<>();
        this.supersets = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        return exercises.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ExercicioWrapper exerciseWrapper = getExerciseWrapper(rowIndex);
        switch (columnIndex) {
            case 0: return exerciseWrapper.getExercicio();
            case 1: return exerciseWrapper.getReps();
            case 2: return exerciseWrapper.getSets();
            case 3: return exerciseWrapper.getDescanso();
            case 4: return supersets.get(exerciseWrapper.getExercicio());
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ExercicioWrapper exercicioWrapper = getExerciseWrapper(rowIndex);
        switch (columnIndex) {
            case 0: exercicioWrapper.setExercicio((Exercicio) aValue); break;
            case 1: exercicioWrapper.setReps((int) aValue); break;
            case 2: exercicioWrapper.setSets((int) aValue); break;
            case 3: exercicioWrapper.setDescanso((int) aValue); break;
            case 4: updateSupersets(exercicioWrapper.getExercicio(), (Exercicio) aValue); break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
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

    private void updateSupersets(Exercicio ex1, Exercicio ex2) {
        if (ex2 != null && ex1 != ex2) 
            addPair(ex1, ex2);
        if (ex2 == null) 
            removePair(ex1);
        fireTableDataChanged();
    }

    /**
     * <p>
     * Adiciona um novo par ao HashMap e remove exercicios "sozinhos".</p>
     * <p>
     * eg.<br> <code>&nbsp;ex1 = ex2; ex2 = ex1; <br>&nbsp;ex3 = ex4; ex4 = ex3;
     * </code></p>
     * <p>
     * Se o valor de algum mudar. ie. <code> ex3 = ex2;</code> <br> Então as
     * keys não utilizadas (ex1=ex2 e ex4=ex3) são removidas do HashMap.</p>
     *
     * @param ex1
     * @param ex2
     */
    private void addPair(Exercicio ex1, Exercicio ex2) {
        Exercicio _ex1 = supersets.get(ex1);
        Exercicio _ex2 = supersets.get(ex2);
        supersets.put(ex1, ex2);
        supersets.put(ex2, ex1);
        if (_ex1 != supersets.get(ex1)) {
            supersets.remove(_ex1);
            supersets.remove(_ex2);
        }
    }

    private void removePair(Exercicio ex1) {
        Exercicio _ex2 = supersets.get(ex1);
        supersets.remove(ex1, _ex2);
        supersets.remove(_ex2, ex1);
    }

    private ExercicioWrapper getExerciseWrapper(int rowIndex) {
        return exercises.get(rowIndex);
    }

    public void addExerciseWrapper(ExercicioWrapper exerciseWrapper) {
        insertExerciseWrapper(getRowCount(), exerciseWrapper);
    }

    public void insertExerciseWrapper(int row, ExercicioWrapper exerciseWrapper) {
        exercises.add(row, exerciseWrapper);
        fireTableRowsInserted(row, row);
    }

    public void removeExerciseWrapper(int row) {
        Exercicio exercise = (Exercicio) getValueAt(row, 0);
        removePair(exercise);
        exercises.remove(row);
        fireTableDataChanged();
    }

    public ArrayList<ExercicioWrapper> getExercises() {
        return exercises;
    }

    public HashMap<ExercicioWrapper, ExercicioWrapper> getSupersets() {
        HashMap<ExercicioWrapper, ExercicioWrapper> _superset = new HashMap<>();
        for (Map.Entry<Exercicio, Exercicio> set : supersets.entrySet()) {
            Exercicio key = set.getKey();
            Exercicio value = set.getValue();
            ExercicioWrapper newKey = null;
            ExercicioWrapper newValue = null;
            for (ExercicioWrapper exerciseWrapper : exercises) {
                Exercicio currentExercise = exerciseWrapper.getExercicio();
                if (currentExercise.equals(key)) 
                    newKey = exerciseWrapper;
                if (currentExercise.equals(value)) 
                    newValue = exerciseWrapper;
                if (newKey != null && newValue != null) 
                    break;
            }
            _superset.put(newKey, newValue);
        }
        return _superset;
    }

}
