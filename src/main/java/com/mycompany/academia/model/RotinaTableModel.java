/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.model;

import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.relatorio.ExercicioWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        switch (columnIndex) {
            case 0: return exercicio_wrapper.getExercicio();
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

        switch (columnIndex) {
            case 0: exercicioWrapper.setExercicio((Exercicio) aValue); break;
            case 1: exercicioWrapper.setReps((int) aValue); break;
            case 2: exercicioWrapper.setSets((int) aValue); break;
            case 3: exercicioWrapper.setDescanco((int) aValue); break;
            case 4: atualizar_supersets(exercicioWrapper.getExercicio(), (Exercicio) aValue); break;
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return nome_colunas[column];
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

    private void atualizar_supersets(Exercicio ex1, Exercicio ex2) {
        if (ex2 != null && ex1 != ex2) {
            adicionar_par(ex1, ex2);
        }
        if (ex2 == null) {
            remover_par(ex1);
        }
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
    private void adicionar_par(Exercicio ex1, Exercicio ex2) {
        Exercicio _ex1 = superset.get(ex1);
        Exercicio _ex2 = superset.get(ex2);

        superset.put(ex1, ex2);
        superset.put(ex2, ex1);

        if (_ex1 != superset.get(ex1)) {
            superset.remove(_ex1);
            superset.remove(_ex2);
        }
    }

    private void remover_par(Exercicio ex1) {
        Exercicio _ex2 = superset.get(ex1);
        superset.remove(ex1, _ex2);
        superset.remove(_ex2, ex1);
    }

    private ExercicioWrapper getExercicioWrapper(int rowIndex) {
        return exercicios.get(rowIndex);
    }

    public void addExercicioWrapper(ExercicioWrapper exercicioWrapper) {
        insertExercicioWrapper(getRowCount(), exercicioWrapper);
    }

    public void insertExercicioWrapper(int row, ExercicioWrapper exercicioWrapper) {
        exercicios.add(row, exercicioWrapper);
        fireTableRowsInserted(row, row);
    }

    public void removeExercicioWrapper(int row) {
        Exercicio row_exercicio = (Exercicio) getValueAt(row, 0);
        remover_par(row_exercicio);
        exercicios.remove(row);
        fireTableDataChanged();
    }

    public ArrayList<ExercicioWrapper> getExercicios() {
        return exercicios;
    }

    public HashMap<ExercicioWrapper, ExercicioWrapper> getSuperset() {
        HashMap<ExercicioWrapper, ExercicioWrapper> _superset = new HashMap<>();
        for (Map.Entry<Exercicio, Exercicio> set : superset.entrySet()) {
            Exercicio key = set.getKey();
            Exercicio value = set.getValue();
            ExercicioWrapper new_key = null;
            ExercicioWrapper new_value = null;

            for (ExercicioWrapper exwpr : exercicios) {
                Exercicio cur_ex = exwpr.getExercicio();
                if (cur_ex.equals(key)) {
                    new_key = exwpr;
                }
                if (cur_ex.equals(value)) {
                    new_value = exwpr;
                }
                if (new_key != null && new_value != null) {
                    break;
                }
            }
            _superset.put(new_key, new_value);
        }
        return _superset;
    }

}
