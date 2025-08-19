/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.musculoalvo;

import com.mycompany.academia.controle.MusculoAlvoControle;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author paulo
 */
public class MusculoAlvoEdita extends JDialog {

    private final MusculoAlvoEditaPanel musculoAlvoEditaPanel;

    public MusculoAlvoEdita(JFrame parentWindow, MusculoAlvoControle musculoAlvoControle, MusculoAlvo musculoAlvo) {
        musculoAlvoEditaPanel = new MusculoAlvoEditaPanel(this, musculoAlvoControle, musculoAlvo);
        setContentPane(musculoAlvoEditaPanel);
        pack();
        setModal(true);
        setTitle("Edita Músculo-Alvo");
        setLocationRelativeTo(parentWindow);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private class MusculoAlvoEditaPanel extends JPanel {

        private final MusculoAlvoControle musculoAlvoControle;
        private final MusculoAlvo musculoAlvo;
        private final JDialog parentWindow;

        private final GridBagLayout gridBagLayout;
        private JLabel idLabel;
        private JTextField idInput;
        private JLabel nomeLabel;
        private JTextField nomeInput;
        private JButton salvar;
        private JButton cancelar;

        public MusculoAlvoEditaPanel(JDialog parentWindow, MusculoAlvoControle musculoAlvoControle, MusculoAlvo musculoAlvo) {
            this.musculoAlvoControle = musculoAlvoControle;
            this.musculoAlvo = musculoAlvo;
            this.parentWindow = parentWindow;
            gridBagLayout = new GridBagLayout();

            setLayout(gridBagLayout);
            initComponents();
            addComponents();
        }

        private void sair() {
            parentWindow.dispose();
        }

        private void cancelarActionPerformed() {
            sair();
        }

        private boolean inputValido() {
            if (nomeInput.getText().isBlank()) {
                String mensagem = "O campo Nome precisa ser preenchido";
                JOptionPane.showMessageDialog(this, mensagem);
                return false;
            }
            return true;
        }

        private void salvarActionPerformed() {
            if (inputValido()) {
                musculoAlvo.setNomeAlvo(nomeInput.getText());
                musculoAlvoControle.salvar(musculoAlvo);
                sair();
            }
        }

        private void initComponents() {
            idLabel = new JLabel("ID");
            idInput = new JTextField();
            idInput.setEditable(false);
            idInput.setFocusable(false);
            Integer idVal = musculoAlvo.getIdAlvo();
            idInput.setText((idVal != null) ? idVal.toString() : "");

            nomeLabel = new JLabel("Nome");
            nomeInput = new JTextField();

            salvar = new JButton("Salvar");
            salvar.addActionListener((e) -> {
                salvarActionPerformed();
            });

            cancelar = new JButton("Cancelar");
            cancelar.addActionListener((e) -> {
                cancelarActionPerformed();
            });
        }

        private void addComponents() {

            GridBagConstraints labels = new GridBagConstraints();
            GridBagConstraints inputField = new GridBagConstraints();
            GridBagConstraints botoes = new GridBagConstraints();

            labels.anchor = GridBagConstraints.LINE_END;
            labels.insets = new Insets(10, 10, 5, 5);
            labels.gridwidth = 1;
            labels.gridx = 0;
            labels.gridy = 0;

            inputField.anchor = GridBagConstraints.FIRST_LINE_START;
            inputField.fill = GridBagConstraints.BOTH;
            inputField.weightx = 0.2d;
            inputField.weighty = 0.2d;
            inputField.insets = new Insets(10, 5, 5, 10);
            inputField.gridwidth = 2;
            inputField.gridx = 1;
            inputField.gridy = 0;

            botoes.anchor = GridBagConstraints.FIRST_LINE_START;
            botoes.fill = GridBagConstraints.BOTH;
            botoes.weightx = 0.2d;
            botoes.weighty = 0.2d;
            botoes.insets = new Insets(10, 5, 10, 5);
            botoes.gridwidth = 1;
            botoes.gridx = 1;
            botoes.gridy = 2;

            add(idLabel, labels);
            labels.gridy++;
            add(nomeLabel, labels);

            add(idInput, inputField);
            inputField.gridy++;
            add(nomeInput, inputField);

            add(cancelar, botoes);
            botoes.gridx++;
            add(salvar, botoes);

        }

    }

}
