/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.routine;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.RoutineController;
import com.mycompany.academia.model.dto.ExercicioWrapper;
import com.mycompany.academia.model.dto.Sublista;
import com.mycompany.academia.model.entity.Exercicio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author paulo
 */
public final class RoutineContent extends JPanel {

    private final RoutineController routineController;
    private int tabCount;
    private JTabbedPane tabs;
    private JPanel operations;
    private JButton jButtonReset;
    private JButton jButtonAddTab;
    private JButton jButtonRemoveExercise;
    private JButton jButtonRemoveTab;
    private JButton jButtonPrint;

    public RoutineContent(RoutineController routineController) {
        this.routineController = routineController;
        tabCount = 0;
        initComponents();
        addTab();
    }

    public void addExercise(Exercicio exercise) {
        ExercicioWrapper newExercise = new ExercicioWrapper(exercise, 12, 3, 30);
        JScrollPane activeTab = (JScrollPane) tabs.getSelectedComponent();
        JTable table = (JTable) activeTab.getViewport().getView();
        RoutineTableModel tableModel = (RoutineTableModel) table.getModel();
        tableModel.addExerciseWrapper(newExercise);
        updateComboBox(table);
    }

    private void updateComboBox(JTable table) {
        JComboBox<Exercicio> comboBox = new JComboBox<>();
        int lineCount = table.getModel().getRowCount();
        for (int i = 0; i < lineCount; i++) {
            Exercicio exercise = (Exercicio) table.getModel().getValueAt(i, 0);
            comboBox.addItem(exercise);
        }
        comboBox.addItem(null);
        TableColumn superSetColumn = table.getColumnModel().getColumn(4);
        superSetColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    private void addTab() {
        JTable table = new JTable(new RoutineTableModel());
        updateComboBox(table);
        String letter = Character.toString(65 + tabCount++); // A different letter for every created tab
        tabs.insertTab(letter, null, new JScrollPane(table), "", tabs.getTabCount());
    }

    private void removeExercise() {
        JScrollPane activeTab = (JScrollPane) tabs.getSelectedComponent();
        JTable table = (JTable) activeTab.getViewport().getView();
        RoutineTableModel tableModel = (RoutineTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this,
                    LocalizationManager.getString("localization.Strings", "routine.content.error.dialog"));
            return;
        }
        tableModel.removeExerciseWrapper(selectedRow);
        updateComboBox(table);
    }

    private void deleteTab() {
        if (tabs.getTabCount() == 1)
            return;
        int selectTabIndex = tabs.getSelectedIndex();
        tabs.remove(selectTabIndex);
    }

    private void reset() {
        tabs.removeAll();
        tabCount = 0;
        addTab();
    }

    private void print() {
        ArrayList<String> titlesList = getTitles();
        ArrayList<ArrayList<ExercicioWrapper>> exercisesList = getExercisesList();
        ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superSetList = getSupersetList();
        Collection<Sublista> dataset = new ArrayList<>();
        for (int i = 0; i < exercisesList.size(); i++) {
            String currentTitle = titlesList.get(i);
            HashMap<ExercicioWrapper, ExercicioWrapper> currentSuperset = superSetList.get(i);
            ArrayList<ExercicioWrapper> currentExerciseList = new ArrayList<>(exercisesList.get(i)); // Cria uma nova
                                                                                                     // lista para
                                                                                                     // não alterar a
                                                                                                     // lista
                                                                                                     // renderizada
                                                                                                     // na interface.
            ArrayList<ExercicioWrapper> currentSupersetList = new ArrayList<>(); // Se um exercício está em superset
                                                                                 // adiciona para listaSuperset e remove
                                                                                 // de listaExercicios.
            for (Map.Entry<ExercicioWrapper, ExercicioWrapper> entry : currentSuperset.entrySet()) {
                ExercicioWrapper exerciseWrapper = entry.getKey();
                if (!currentSupersetList.contains(exerciseWrapper)) {
                    currentSupersetList.add(exerciseWrapper);
                    currentSupersetList.add(currentSuperset.get(exerciseWrapper));
                    currentExerciseList.remove(exerciseWrapper);
                    currentExerciseList.remove(currentSuperset.get(exerciseWrapper));
                }
            }
            dataset.add(new Sublista(currentTitle, currentExerciseList, currentSupersetList));
        }
        routineController.printReport(dataset);
    }

    private ArrayList<String> getTitles() {
        ArrayList<String> titlesList = new ArrayList<>();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            String title = tabs.getTitleAt(i);
            titlesList.add(title);
        }
        return titlesList;
    }

    private ArrayList<ArrayList<ExercicioWrapper>> getExercisesList() {
        ArrayList<ArrayList<ExercicioWrapper>> exercisesList = new ArrayList<>();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            JScrollPane currentTab = (JScrollPane) tabs.getComponentAt(i);
            JTable table = (JTable) currentTab.getViewport().getView();
            RoutineTableModel tableModel = (RoutineTableModel) table.getModel();
            ArrayList<ExercicioWrapper> exercises = tableModel.getExercises();
            exercisesList.add(exercises);
        }
        return exercisesList;
    }

    private ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> getSupersetList() {
        ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> supersetList = new ArrayList<>();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            JScrollPane currentTab = (JScrollPane) tabs.getComponentAt(i);
            JTable table = (JTable) currentTab.getViewport().getView();
            RoutineTableModel tableModel = (RoutineTableModel) table.getModel();
            HashMap<ExercicioWrapper, ExercicioWrapper> superset = tableModel.getSupersets();
            supersetList.add(superset);
        }
        return supersetList;
    }

    private void initComponents() {
        tabs = new JTabbedPane();
        operations = new JPanel();

        float alignment = 0.7f;
        Dimension minimum = new Dimension(0, 0);
        Dimension buttonGap = new Dimension(0, 20);
        Dimension topGap = new Dimension(60, 30);

        jButtonReset = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.content.button.reset"));
        jButtonReset.setAlignmentX(alignment - 0.23f);
        jButtonReset.setBackground(new Color(204, 0, 0));
        jButtonReset.setForeground(new Color(255, 255, 255));
        jButtonReset.addActionListener((e) -> {
            reset();
        });

        jButtonAddTab = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.content.button.add.tab"));
        jButtonAddTab.setAlignmentX(alignment - 0.07f);
        jButtonAddTab.addActionListener((e) -> {
            addTab();
        });

        jButtonRemoveExercise = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.content.button.remove.exercise"));
        jButtonRemoveExercise.setAlignmentX(alignment);
        jButtonRemoveExercise.addActionListener((e) -> {
            removeExercise();
        });

        jButtonRemoveTab = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.content.button.remove.tab"));
        jButtonRemoveTab.setAlignmentX(alignment - 0.08f);
        jButtonRemoveTab.addActionListener((e) -> {
            deleteTab();
        });

        jButtonPrint = new JButton(
                LocalizationManager.getString("localization.Strings", "routine.content.button.print"));
        jButtonPrint.setAlignmentX(alignment - 0.22f);
        jButtonPrint.addActionListener((e) -> {
            print();
        });

        BoxLayout boxLayout = new BoxLayout(operations, BoxLayout.Y_AXIS);
        operations.setLayout(boxLayout);
        operations.add(new Box.Filler(minimum, topGap, topGap));
        operations.add(jButtonReset);
        operations.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operations.add(jButtonAddTab);
        operations.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operations.add(jButtonRemoveExercise);
        operations.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operations.add(jButtonRemoveTab);
        operations.add(new Box.Filler(minimum, buttonGap, buttonGap));
        operations.add(jButtonPrint);

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(819, 551));
        add(tabs, BorderLayout.CENTER);
        add(operations, BorderLayout.EAST);
    }

}
