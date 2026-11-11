/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.muscle;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.TargetMuscleController;
import com.mycompany.academia.model.entity.MusculoAlvo;

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
import com.mycompany.academia.view.ListTableOperation;

/**
 *
 * @author paulo
 */
public final class TargetMuscleList extends JFrame {

    private final JPanelTargetMuscleList jPanelTargetMuscleList;

    public TargetMuscleList(JFrame parentWindow, TargetMuscleController targetMuscleController)
            throws HeadlessException {
        jPanelTargetMuscleList = new JPanelTargetMuscleList(this, targetMuscleController);
        setContentPane(jPanelTargetMuscleList);
        pack();
        setTitle(LocalizationManager.getString("localization.Strings", "muscle.list.window.title"));
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private class JPanelTargetMuscleList extends JPanel implements ListTableOperation {

        private final JFrame parentWindow;
        private final BorderLayout borderLayout;
        private final TargetMuscleController targetMuscleController;
        private final JScrollPaneListTable jScrollPaneListTable;
        private final JPanelMenu jPanelMenu;

        public JPanelTargetMuscleList(JFrame parentWindow, TargetMuscleController targetMuscleController) {
            this.parentWindow = parentWindow;
            this.targetMuscleController = targetMuscleController;
            borderLayout = new BorderLayout();
            jScrollPaneListTable = new JScrollPaneListTable(targetMuscleController);
            jPanelMenu = new JPanelMenu(this);
            setLayout(borderLayout);
            add(jScrollPaneListTable, BorderLayout.CENTER);
            add(jPanelMenu, BorderLayout.EAST);
        }

        @Override
        public void create() {
            new TargetMuscleEdit(parentWindow, targetMuscleController, new MusculoAlvo()).setVisible(true);
        }

        @Override
        public void edit() {
            MusculoAlvo selected = jScrollPaneListTable.getSelected();
            if (selected != null) {
                new TargetMuscleEdit(parentWindow, targetMuscleController, selected).setVisible(true);
            }
        }

        @Override
        public void delete() {
            MusculoAlvo selected = jScrollPaneListTable.getSelected();
            if (selected != null) {
                var deleteWarning = LocalizationManager.getFormattedString("localization.Strings",
                        "muscle.list.delete.warning.message", selected.getNomeAlvo(), selected.getIdAlvo());
                var buttonConfirm = LocalizationManager.getString("localization.Strings",
                        "muscle.list.delete.confirmation.message");
                int confirmarDeletar = JOptionPane.showConfirmDialog(this, deleteWarning, buttonConfirm,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (confirmarDeletar == JOptionPane.OK_OPTION) {
                    targetMuscleController.delete(selected);
                }
            }
        }

        private class JScrollPaneListTable extends JScrollPane {

            private final JTable jTable;
            private final TargetMuscleController targetMuscleController;

            public JScrollPaneListTable(TargetMuscleController targetMuscleController) {
                this.targetMuscleController = targetMuscleController;
                jTable = new JTable(targetMuscleController.getTargetMuscleTableModel());
                jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jTable.getColumnModel().getColumn(0).setMaxWidth(50); // Makes Id column smaller
                setViewportView(jTable);
            }

            private MusculoAlvo getSelected() {
                int selectedLineNumber = jTable.getSelectedRow();
                if (selectedLineNumber == -1)
                    return null;
                int targetMuscleId = (Integer) jTable.getValueAt(selectedLineNumber, 0);
                return targetMuscleController.findById(targetMuscleId);
            }
        }

        private class JPanelMenu extends JPanel {

            private final ListTableOperation parentWindow;
            private final BoxLayout boxLayout;
            private JButton jButtonCreate;
            private JButton jButtonDelete;
            private JButton jButtonEdit;

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

                jButtonCreate = new JButton(
                        LocalizationManager.getString("localization.Strings", "muscle.list.button.create"));
                jButtonCreate.setAlignmentX(alignment - 0.07f);
                jButtonCreate.addActionListener((e) -> {
                    requestCreateWindow();
                });

                jButtonDelete = new JButton(
                        LocalizationManager.getString("localization.Strings", "muscle.list.button.delete"));
                jButtonDelete.setAlignmentX(alignment);
                jButtonDelete.addActionListener((e) -> {
                    requestDeletion();
                });

                jButtonEdit = new JButton(
                        LocalizationManager.getString("localization.Strings", "muscle.list.button.edit"));
                jButtonEdit.setAlignmentX(alignment - 0.03f);
                jButtonEdit.addActionListener((e) -> {
                    requestEditWindow();
                });

                add(new Box.Filler(minimum, topGap, topGap));
                add(jButtonCreate);
                add(new Box.Filler(minimum, buttonGap, buttonGap));
                add(jButtonDelete);
                add(new Box.Filler(minimum, buttonGap, buttonGap));
                add(jButtonEdit);
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
