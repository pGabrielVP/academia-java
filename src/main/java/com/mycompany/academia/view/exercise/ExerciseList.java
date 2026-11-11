/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercise;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.ExerciseController;
import com.mycompany.academia.model.entity.Exercicio;
import com.mycompany.academia.view.ListTableOperation;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

/**
 *
 * @author paulo
 */
public final class ExerciseList extends JFrame {

    private final JPanelExerciseList exerciseListPanel;

    public ExerciseList(JFrame parentWindow, ExerciseController exerciseController) throws HeadlessException {
        exerciseListPanel = new JPanelExerciseList(this, exerciseController);
        setContentPane(exerciseListPanel);
        pack();
        setTitle(LocalizationManager.getString("localization.Strings", "exercise.list.window.title"));
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private class JPanelExerciseList extends JPanel implements ListTableOperation {

        private final JFrame parentWindow;
        private final BorderLayout borderLayout;
        private final ExerciseController exerciseController;
        private final JScrollPaneExerciseList jTableExerciseList;
        private final JPanelMenu jPanelMenu;

        public JPanelExerciseList(JFrame parentWindow, ExerciseController exerciseController) {
            this.parentWindow = parentWindow;
            this.exerciseController = exerciseController;
            borderLayout = new BorderLayout();
            jTableExerciseList = new JScrollPaneExerciseList(exerciseController);
            jPanelMenu = new JPanelMenu(this);
            setLayout(borderLayout);
            add(jTableExerciseList, BorderLayout.CENTER);
            add(jPanelMenu, BorderLayout.EAST);
        }

        @Override
        public void create() {
            new ExerciseEdit(parentWindow, exerciseController, new Exercicio()).setVisible(true);
        }

        @Override
        public void edit() {
            Exercicio selected = jTableExerciseList.getSelected();
            if (selected != null) {
                new ExerciseEdit(parentWindow, exerciseController, selected).setVisible(true);
            }
        }

        @Override
        public void delete() {
            Exercicio selected = jTableExerciseList.getSelected();
            if (selected != null) {
                var deleteWarningText = LocalizationManager.getFormattedString("localization.Strings",
                        "exercise.list.delete.warning.message", selected.getNomeExercicio(), selected.getIdExercicio());
                var buttonConfirmText = LocalizationManager.getString("localization.Strings",
                        "exercise.list.delete.confirmation.message");
                int selectedOption = JOptionPane.showConfirmDialog(this, deleteWarningText, buttonConfirmText,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (selectedOption == JOptionPane.OK_OPTION) {
                    exerciseController.delete(selected);
                }
            }
        }

        private class JScrollPaneExerciseList extends JScrollPane {

            private final JTable jTable;
            private final ExerciseController exerciseController;

            public JScrollPaneExerciseList(ExerciseController exerciseController) {
                this.exerciseController = exerciseController;
                jTable = new JTable(exerciseController.getExerciseTableModel());
                jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable.getColumnModel().getColumn(0).setMaxWidth(50); // Makes ID column smaller
                setViewportView(jTable);
            }

            private Exercicio getSelected() {
                int selectedLine = jTable.getSelectedRow();
                if (selectedLine == -1)
                    return null;
                int targetMuscleId = (Integer) jTable.getValueAt(selectedLine, 0);
                return exerciseController.findById(targetMuscleId);
            }
        }

        private class JPanelMenu extends JPanel {

            private final ListTableOperation parentWindow;
            private final BoxLayout boxLayout;
            private JButton buttonCreate;
            private JButton buttonDelete;
            private JButton buttonEdit;

            public JPanelMenu(ListTableOperation parentWindow) {
                this.parentWindow = parentWindow;
                boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
                setLayout(boxLayout);
                initComponents();
            }

            private void initComponents() {
                float alignment = 0.7f;
                Dimension minimum = new Dimension(0, 0);
                Dimension buttonGap = new Dimension(0, 20);
                Dimension topGap = new Dimension(60, 30);

                buttonCreate = new JButton(
                        LocalizationManager.getString("localization.Strings", "exercise.list.button.create"));
                buttonCreate.setAlignmentX(alignment - 0.07f);
                buttonCreate.addActionListener((e) -> {
                    requestCreateWindow();
                });

                buttonDelete = new JButton(
                        LocalizationManager.getString("localization.Strings", "exercise.list.button.delete"));
                buttonDelete.setAlignmentX(alignment);
                buttonDelete.addActionListener((e) -> {
                    requestDeletion();
                });

                buttonEdit = new JButton(
                        LocalizationManager.getString("localization.Strings", "exercise.list.button.edit"));
                buttonEdit.setAlignmentX(alignment - 0.03f);
                buttonEdit.addActionListener((e) -> {
                    requestEditWindow();
                });

                add(new Box.Filler(minimum, topGap, topGap));
                add(buttonCreate);
                add(new Box.Filler(minimum, buttonGap, buttonGap));
                add(buttonDelete);
                add(new Box.Filler(minimum, buttonGap, buttonGap));
                add(buttonEdit);
            }

            private void requestCreateWindow() {
                parentWindow.create();
            }

            private void requestDeletion() {
                parentWindow.delete();
            }

            private void requestEditWindow() {
                parentWindow.edit();
            }

        }

    }

}
