/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercise;

import com.mycompany.academia.LocalizationManager;
import com.mycompany.academia.controller.ExerciseController;
import com.mycompany.academia.model.entity.Exercicio;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.view.effect.ComboBoxValidator;
import com.mycompany.academia.view.effect.TextFieldValidator;
import com.mycompany.academia.view.muscle.TargetMuscleRenderer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.LayerUI;

/**
 *
 * @author paulo
 */
public final class ExerciseEdit extends JDialog {

    private final ExerciseEditPanel exerciseEditPanel;

    public ExerciseEdit(JFrame parentWindow, ExerciseController exerciseController, Exercicio exercise)
            throws HeadlessException {
        exerciseEditPanel = new ExerciseEditPanel(this, exerciseController, exercise);
        setContentPane(exerciseEditPanel);
        pack();
        setModal(true);
        setTitle(LocalizationManager.getString("localization.Strings", "exercise.edit.window.title"));
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private class ExerciseEditPanel extends JPanel {

        private final ExerciseController exerciseController;
        private final Exercicio exercise;
        private final JDialog parentWindow;

        private final GridBagLayout gridBagLayout;
        private final LayerUI<JComboBox<MusculoAlvo>> comboBoxLayerUI;
        private final LayerUI<JTextField> textFieldLayerUI;
        private JLabel labelId;
        private JTextField inputId;
        private JLabel labelName;
        private JTextField inputName;
        private JLabel labelImage;
        private JTextField inputImage;
        private JButton buttonImage;
        private byte[] selectedImage;
        private JLabel labelTargetMuscle;
        private JComboBox<MusculoAlvo> inputTargetMuscle;
        private JButton buttonSave;
        private JButton buttonCancel;

        public ExerciseEditPanel(JDialog parentWindow, ExerciseController exerciseController, Exercicio exercise) {
            this.parentWindow = parentWindow;
            this.exerciseController = exerciseController;
            this.exercise = exercise;
            this.selectedImage = exercise.getImagem();
            gridBagLayout = new GridBagLayout();
            comboBoxLayerUI = new ComboBoxValidator();
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
                exercise.setNomeExercicio(inputName.getText());
                exercise.setImagem(selectedImage);
                exercise.setMusculoAlvo((MusculoAlvo) inputTargetMuscle.getSelectedItem());
                exerciseController.save(exercise);
                quit();
            }
        }

        private boolean isInputValid() {
            var errors = new ArrayList<String>();
            if (inputName.getText().isBlank())
                errors.add(LocalizationManager.getString("localization.Strings", "exercise.edit.label.name"));
            if (inputTargetMuscle.getSelectedItem() == null)
                errors.add(LocalizationManager.getString("localization.Strings", "exercise.edit.label.muscle"));
            if (selectedImage == null)
                errors.add(LocalizationManager.getString("localization.Strings", "exercise.edit.label.image"));
            if (errors.size() == 0)
                return true;
            var errorMessage = LocalizationManager.getFormattedString("localization.Strings",
                    "exercise.edit.error.message", errors.size(), String.join(", ", errors));
            JOptionPane.showMessageDialog(this, errorMessage);
            return false;
        }

        private void inputImageFocusGained() {
            if (selectedImage != null) {
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(selectedImage);
                    BufferedImage image = ImageIO.read(byteArrayInputStream);
                    var imageString = LocalizationManager.getString("localization.Strings",
                            "exercise.edit.label.image");
                    JOptionPane.showMessageDialog(this, new JLabel(new ImageIcon(image)), imageString,
                            JOptionPane.PLAIN_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(ExerciseEdit.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                requestFileChooser();
            }
        }

        private void requestFileChooser() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            var fileFormat = "png";
            var allowedFormats = LocalizationManager.getFormattedString("localization.Strings",
                    "exercise.edit.accepted.formats", fileFormat);
            fileChooser.setFileFilter(new FileNameExtensionFilter(allowedFormats, fileFormat));

            int fileChooserState = fileChooser.showOpenDialog(this);
            if (fileChooserState == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    BufferedImage bufferedImage = ImageIO.read(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, fileFormat, baos);

                    selectedImage = baos.toByteArray();
                    inputImage.setText(file.getCanonicalPath());
                } catch (IOException ex) {
                    Logger.getLogger(ExerciseEditPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void initComponents() {

            GridBagConstraints labelConstraints = new GridBagConstraints();
            GridBagConstraints inputConstraints = new GridBagConstraints();
            GridBagConstraints buttonConstraints = new GridBagConstraints();

            labelConstraints.anchor = GridBagConstraints.LINE_END;
            labelConstraints.insets = new Insets(10, 10, 5, 5);
            labelConstraints.gridwidth = 1;
            labelConstraints.gridx = 0;
            labelConstraints.gridy = 0;

            inputConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            inputConstraints.fill = GridBagConstraints.BOTH;
            inputConstraints.weightx = 0.2d;
            inputConstraints.weighty = 0.2d;
            inputConstraints.insets = new Insets(10, 5, 5, 10);
            inputConstraints.gridwidth = 2;
            inputConstraints.gridx = 1;
            inputConstraints.gridy = 0;

            buttonConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
            buttonConstraints.fill = GridBagConstraints.BOTH;
            buttonConstraints.weightx = 0.2d;
            buttonConstraints.weighty = 0.2d;
            buttonConstraints.insets = new Insets(10, 5, 10, 5);
            buttonConstraints.gridwidth = 1;
            buttonConstraints.gridx = 1;
            buttonConstraints.gridy = 4;

            labelId = new JLabel(LocalizationManager.getString("localization.Strings", "exercise.edit.label.id"));
            inputId = new JTextField();
            inputId.setEditable(false);
            inputId.setFocusable(false);
            Integer exerciseId = exercise.getIdExercicio();
            inputId.setText((exerciseId != null) ? exerciseId.toString() : "");

            labelName = new JLabel(LocalizationManager.getString("localization.Strings", "exercise.edit.label.name"));
            inputName = new JTextField();
            inputName.setText(exercise.getNomeExercicio());

            labelImage = new JLabel(
                    LocalizationManager.getString("localization.Strings", "exercise.edit.label.image"));
            inputImage = new JTextField();
            inputImage.setEditable(false);
            inputImage.setFocusable(true);
            String inputImageText = (selectedImage != null) ? selectedImage.toString() : "";
            inputImage.setText(inputImageText);
            inputImage.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    e.getComponent().setFocusable(false);
                    inputImageFocusGained();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    e.getComponent().setFocusable(true);
                }
            });
            buttonImage = new JButton(
                    LocalizationManager.getString("localization.Strings", "exercise.edit.button.image.select"));
            buttonImage.addActionListener((e) -> {
                requestFileChooser();
            });

            labelTargetMuscle = new JLabel(
                    LocalizationManager.getString("localization.Strings", "exercise.edit.label.muscle"));
            inputTargetMuscle = new JComboBox<>();
            inputTargetMuscle.setModel(
                    new DefaultComboBoxModel<>(exerciseController.getTargetMuscleList().toArray(MusculoAlvo[]::new)));
            inputTargetMuscle.setRenderer(new TargetMuscleRenderer());
            inputTargetMuscle.setSelectedItem(exercise.getMusculoAlvo());
            inputTargetMuscle.setEditable(false);

            buttonSave = new JButton(
                    LocalizationManager.getString("localization.Strings", "exercise.edit.button.save"));
            buttonSave.addActionListener((e) -> {
                saveActionPerformed();
            });
            buttonCancel = new JButton(
                    LocalizationManager.getString("localization.Strings", "exercise.edit.button.cancel"));
            buttonCancel.addActionListener((e) -> {
                cancelActionPerformed();
            });

            add(labelId, labelConstraints);
            labelConstraints.gridy++;
            add(labelName, labelConstraints);
            labelConstraints.gridy++;
            add(labelImage, labelConstraints);
            labelConstraints.gridy++;
            add(labelTargetMuscle, labelConstraints);

            add(inputId, inputConstraints);
            inputConstraints.gridy++;
            add(new JLayer<>(inputName, textFieldLayerUI), inputConstraints);
            inputConstraints.gridy++;
            add(new JLayer<>(inputImage, textFieldLayerUI), inputConstraints);
            inputConstraints.gridx += 2;
            inputConstraints.gridwidth = 1;
            add(buttonImage, inputConstraints);
            inputConstraints.gridwidth = 2;
            inputConstraints.gridx -= 2;
            inputConstraints.gridy++;
            add(new JLayer<>(inputTargetMuscle, comboBoxLayerUI), inputConstraints);

            add(buttonCancel, buttonConstraints);
            buttonConstraints.gridx++;
            add(buttonSave, buttonConstraints);
        }

    }

}
