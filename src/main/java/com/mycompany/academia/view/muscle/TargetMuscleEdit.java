/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.muscle;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.TargetMuscleController;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.view.effect.TextFieldValidator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author paulo
 */
public final class TargetMuscleEdit extends JDialog {

    private final JPanelTargetMuscleEdit jPanelTargetMuscleEdit;

    public TargetMuscleEdit(JFrame parentWindow, TargetMuscleController targetMuscleController,
            MusculoAlvo targetMuscle) {
        jPanelTargetMuscleEdit = new JPanelTargetMuscleEdit(this, targetMuscleController, targetMuscle);
        setContentPane(jPanelTargetMuscleEdit);
        pack();
        setModal(true);
        setTitle(LocalizationManager.getString("localization.Strings", "muscle.edit.window.title"));
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private class JPanelTargetMuscleEdit extends JPanel {

        private final TargetMuscleController targetMuscleController;
        private final MusculoAlvo targetMuscle;
        private final JDialog parentWindow;

        private final GridBagLayout gridBagLayout;
        private final LayerUI<JTextField> textFieldLayerUI;
        private JLabel jLabelId;
        private JTextField jTextFieldId;
        private JLabel jLabelName;
        private JTextField jTextFieldName;
        private JButton jButtonSave;
        private JButton jButtonCancel;

        public JPanelTargetMuscleEdit(JDialog parentWindow, TargetMuscleController targetMuscleController,
                MusculoAlvo targetMuscle) {
            this.targetMuscleController = targetMuscleController;
            this.targetMuscle = targetMuscle;
            this.parentWindow = parentWindow;
            gridBagLayout = new GridBagLayout();
            textFieldLayerUI = new TextFieldValidator();

            setLayout(gridBagLayout);
            initComponents();
        }

        private void cancelActionPerformed() {
            quit();
        }

        private void quit() {
            parentWindow.dispose();
        }

        private void saveActionPerformed() {
            if (isInputValid()) {
                targetMuscle.setNomeAlvo(jTextFieldName.getText());
                targetMuscleController.save(targetMuscle);
                quit();
            }
        }

        private boolean isInputValid() {
            if (jTextFieldName.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        LocalizationManager.getString("localization.Strings", "muscle.edit.error.name.message"));
                return false;
            }
            return true;
        }

        private void initComponents() {
            jLabelId = new JLabel(LocalizationManager.getString("localization.Strings", "muscle.edit.label.id"));
            jTextFieldId = new JTextField();
            jTextFieldId.setEditable(false);
            jTextFieldId.setFocusable(false);
            Integer targetMuscleId = targetMuscle.getIdAlvo();
            jTextFieldId.setText((targetMuscleId != null) ? targetMuscleId.toString() : "");

            jLabelName = new JLabel(LocalizationManager.getString("localization.Strings", "muscle.edit.label.name"));
            jTextFieldName = new JTextField();
            String targetMuscleName = targetMuscle.getNomeAlvo();
            jTextFieldName.setText(targetMuscleName);

            jButtonSave = new JButton(LocalizationManager.getString("localization.Strings", "muscle.edit.label.save"));
            jButtonSave.addActionListener((e) -> {
                saveActionPerformed();
            });

            jButtonCancel = new JButton(
                    LocalizationManager.getString("localization.Strings", "muscle.edit.label.cancel"));
            jButtonCancel.addActionListener((e) -> {
                cancelActionPerformed();
            });

            GridBagConstraints labelConstraint = new GridBagConstraints();
            GridBagConstraints inputFieldConstraint = new GridBagConstraints();
            GridBagConstraints buttonConstraint = new GridBagConstraints();

            labelConstraint.anchor = GridBagConstraints.LINE_END;
            labelConstraint.insets = new Insets(10, 10, 5, 5);
            labelConstraint.gridwidth = 1;
            labelConstraint.gridx = 0;
            labelConstraint.gridy = 0;

            inputFieldConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
            inputFieldConstraint.fill = GridBagConstraints.BOTH;
            inputFieldConstraint.weightx = 0.2d;
            inputFieldConstraint.weighty = 0.2d;
            inputFieldConstraint.insets = new Insets(10, 5, 5, 10);
            inputFieldConstraint.gridwidth = 2;
            inputFieldConstraint.gridx = 1;
            inputFieldConstraint.gridy = 0;

            buttonConstraint.anchor = GridBagConstraints.FIRST_LINE_START;
            buttonConstraint.fill = GridBagConstraints.BOTH;
            buttonConstraint.weightx = 0.2d;
            buttonConstraint.weighty = 0.2d;
            buttonConstraint.insets = new Insets(10, 5, 10, 5);
            buttonConstraint.gridwidth = 1;
            buttonConstraint.gridx = 1;
            buttonConstraint.gridy = 2;

            add(jLabelId, labelConstraint);
            labelConstraint.gridy++;
            add(jLabelName, labelConstraint);

            add(jTextFieldId, inputFieldConstraint);
            inputFieldConstraint.gridy++;
            add(new JLayer<>(jTextFieldName, textFieldLayerUI), inputFieldConstraint);

            add(jButtonCancel, buttonConstraint);
            buttonConstraint.gridx++;
            add(jButtonSave, buttonConstraint);
        }

    }

}
