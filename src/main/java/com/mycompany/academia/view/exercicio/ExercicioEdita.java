/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercicio;

import com.mycompany.academia.controle.ExercicioControle;
import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.view.musculoalvo.MusculoAlvoRenderer;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author paulo
 */
public final class ExercicioEdita extends JDialog {

    private final ExercicioEditaPanel exercicioEditaPanel;

    public ExercicioEdita(JFrame parentWindow, ExercicioControle exercicioControle, Exercicio exercicio) throws HeadlessException {
        exercicioEditaPanel = new ExercicioEditaPanel(this, exercicioControle, exercicio);
        setContentPane(exercicioEditaPanel);
        pack();
        setModal(true);
        setTitle("Exercício Edita");
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private class ExercicioEditaPanel extends JPanel {

        private final ExercicioControle exercicioControle;
        private final Exercicio exercicio;
        private final JDialog parentWindow;

        private final GridBagLayout gridBagLayout;
        private JLabel idLabel;
        private JTextField idInput;
        private JLabel nomeLabel;
        private JTextField nomeInput;
        private JLabel imagemLabel;
        private JTextField imagemInput;
        private JButton imagemButton;
        private byte[] imagemSelecionada;
        private JLabel musculoAlvoLabel;
        private JComboBox<MusculoAlvo> musculoAlvoInput;
        private JButton salvar;
        private JButton cancelar;

        public ExercicioEditaPanel(JDialog parentWindow, ExercicioControle exercicioControle, Exercicio exercicio) {
            this.parentWindow = parentWindow;
            this.exercicioControle = exercicioControle;
            this.exercicio = exercicio;
            this.imagemSelecionada = exercicio.getImagem();
            gridBagLayout = new GridBagLayout();
            setLayout(gridBagLayout);
            initComponents();
        }

        private void cancelarActionPerformed() {
            sair();
        }

        private void sair() {
            parentWindow.dispose();
        }

        private void salvarActionPerformed() {
            if (inputValido()) {
                exercicio.setNomeExercicio(nomeInput.getText());
                exercicio.setImagem(imagemSelecionada);
                exercicio.setMusculoAlvo((MusculoAlvo) musculoAlvoInput.getSelectedItem());
                exercicioControle.salvar(exercicio);
                sair();
            }
        }

        private boolean inputValido() {
            StringBuilder stringBuilder = new StringBuilder();
            int campos = 0;

            if (nomeInput.getText().isBlank()) {
                stringBuilder.append("Nome");
                campos += 1;
            }
            if (musculoAlvoInput.getSelectedItem() == null) {
                stringBuilder.append((stringBuilder.isEmpty()) ? "Músculo-Alvo" : ", Músculo-Alvo");
                campos += 1;
            }
            if (imagemSelecionada == null) {
                stringBuilder.append((stringBuilder.isEmpty()) ? "Imagem" : ", Imagem");
                campos += 1;
            }
            switch (campos) {
                case 0:
                    return true;
                case 1:
                    stringBuilder.insert(0, "O campo: ");
                    stringBuilder.append(" Precisa ser preenchido");
                    break;
                default:
                    stringBuilder.insert(0, "Os campos: ");
                    stringBuilder.append(" Precisam ser preenchidos");
                    break;
            }
            JOptionPane.showMessageDialog(this, stringBuilder.toString());
            return false;
        }

        private void imagemButtonActionPerformed() {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Permite somente imagens .png", "png"));

            int fileChooserState = fileChooser.showOpenDialog(this);
            if (fileChooserState == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    BufferedImage bufferedImage = ImageIO.read(file);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bufferedImage, "png", baos);

                    imagemSelecionada = baos.toByteArray();
                    imagemInput.setText(file.getCanonicalPath());
                } catch (IOException ex) {
                    Logger.getLogger(ExercicioEditaPanel.class.getName()).log(Level.SEVERE, null, ex);
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

            idLabel = new JLabel("ID");
            idInput = new JTextField();
            idInput.setEditable(false);
            idInput.setFocusable(false);
            Integer idExercicio = exercicio.getIdExercicio();
            idInput.setText((idExercicio != null) ? idExercicio.toString() : "");

            nomeLabel = new JLabel("Nome");
            nomeInput = new JTextField();
            nomeInput.setText(exercicio.getNomeExercicio());

            imagemLabel = new JLabel("Imagem");
            imagemInput = new JTextField();
            imagemInput.setEditable(false);
            imagemInput.setFocusable(false);
            String imagemInputText = (imagemSelecionada != null) ? imagemSelecionada.toString() : "";
            imagemInput.setText(imagemInputText);
            imagemButton = new JButton("...");
            imagemButton.addActionListener((e) -> {
                imagemButtonActionPerformed();
            });

            musculoAlvoLabel = new JLabel("Músculo-Alvo");
            musculoAlvoInput = new JComboBox<>();
            musculoAlvoInput.setModel(new DefaultComboBoxModel<>(exercicioControle.getListaMusculoAlvo().toArray(MusculoAlvo[]::new)));
            musculoAlvoInput.setRenderer(new MusculoAlvoRenderer());
            musculoAlvoInput.setSelectedItem(exercicio.getMusculoAlvo());
            musculoAlvoInput.setEditable(false);

            salvar = new JButton("Salvar");
            salvar.addActionListener((e) -> {
                salvarActionPerformed();
            });
            cancelar = new JButton("Cancelar");
            cancelar.addActionListener((e) -> {
                cancelarActionPerformed();
            });

            add(idLabel, labelConstraints);
            labelConstraints.gridy++;
            add(nomeLabel, labelConstraints);
            labelConstraints.gridy++;
            add(imagemLabel, labelConstraints);
            labelConstraints.gridy++;
            add(musculoAlvoLabel, labelConstraints);

            add(idInput, inputConstraints);
            inputConstraints.gridy++;
            add(nomeInput, inputConstraints);
            inputConstraints.gridy++;
            add(imagemInput, inputConstraints);
            inputConstraints.gridx += 2;
            inputConstraints.gridwidth = 1;
            add(imagemButton, inputConstraints);
            inputConstraints.gridwidth = 2;
            inputConstraints.gridx -= 2;
            inputConstraints.gridy++;
            add(musculoAlvoInput, inputConstraints);

            add(cancelar, buttonConstraints);
            buttonConstraints.gridx++;
            add(salvar, buttonConstraints);
        }

    }

}
