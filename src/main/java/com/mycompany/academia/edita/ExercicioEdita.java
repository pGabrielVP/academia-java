/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.edita;

import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.controle.MovimentoJpaController;
import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.Movimento;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.swing.JDialog;

/**
 *
 * @author paulo
 */
public class ExercicioEdita extends javax.swing.JPanel {

    // conexão com o banco de dados 
    private MovimentoJpaController movimentoJpaController = new MovimentoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    private ExercicioJpaController exercicioJpaController = new ExercicioJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));

    // dados para o combobox
    private Movimento[] listaMovimento = movimentoJpaController.findMovimentoEntities().toArray(Movimento[]::new);

    // objeto sendo criado ou editado
    // janela para ser fechada quando a transação com o banco de dados for concluida
    private Exercicio exercicio;
    private JDialog owner;

    /**
     * Creates new form edita
     *
     * @param ex
     * @param parent
     */
    public ExercicioEdita(Exercicio ex, JDialog parent) {
        exercicio = ex;
        owner = parent;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelId = new javax.swing.JLabel();
        labelMovimento = new javax.swing.JLabel();
        labelNome = new javax.swing.JLabel();
        entradaId = new javax.swing.JTextField();
        botaoSalvar = new javax.swing.JButton();
        EntradaNome = new javax.swing.JTextField();
        EntradaMovimento = new javax.swing.JComboBox<>();

        setMinimumSize(new java.awt.Dimension(280, 212));

        labelId.setText("Id");

        labelMovimento.setText("Movimento");

        labelNome.setText("Nome");

        entradaId.setEditable(false);
        entradaId.setColumns(8);
        entradaId.setToolTipText("Id do exercicio");
        entradaId.setFocusable(false);
        if (exercicio.getId() != null) {
            entradaId.setText(exercicio.getId().toString());
        }

        botaoSalvar.setText("Salvar");
        botaoSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botaoSalvarActionPerformed(evt);
            }
        });

        EntradaNome.setColumns(8);
        EntradaNome.setToolTipText("Nome do Exercicio");
        if (exercicio.getNome() != null) {
            EntradaNome.setText(exercicio.getNome());
        }

        EntradaMovimento.setModel(new javax.swing.DefaultComboBoxModel<>(listaMovimento));
        EntradaMovimento.setToolTipText("exemplo de movimento do exercicio");
        if (exercicio.getMovimento() != null) {
            EntradaMovimento.setSelectedItem(exercicio.getMovimento());
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelId)
                            .addComponent(labelMovimento)
                            .addComponent(labelNome))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EntradaMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(entradaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EntradaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(botaoSalvar))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelId)
                    .addComponent(entradaId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EntradaNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelNome))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelMovimento)
                    .addComponent(EntradaMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(botaoSalvar)
                .addContainerGap(67, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botaoSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botaoSalvarActionPerformed
        // se o objeto sendo editado nao tiver id, id for null, cria um objeto novo no banco de dados
        // se o objeto sendo editado ja tiver um id atuliza o objeto e salvo no banco de dados.
        if (exercicio.getId() == null) {
            // define nome e movimento do objeto
            exercicio.setNome(EntradaNome.getText());
            exercicio.setMovimento((Movimento) EntradaMovimento.getSelectedItem());

            // cria o novo objeto no banco de dados e fecha JDialog
            exercicioJpaController.create(exercicio);
            owner.dispose();
        } else {
            // define o nome e o movimento do objeto e salva no banco de dados
            exercicio.setNome(EntradaNome.getText());
            exercicio.setMovimento((Movimento) EntradaMovimento.getSelectedItem()); // TODO: Arruma isso aqui

            // atualiza o objeto no banco de dados
            try {
                exercicioJpaController.edit(exercicio);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ExercicioEdita.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ExercicioEdita.class.getName()).log(Level.SEVERE, null, ex);
            }
            // fecha o JDialog
            owner.dispose();
        }

    }//GEN-LAST:event_botaoSalvarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Object> EntradaMovimento;
    private javax.swing.JTextField EntradaNome;
    private javax.swing.JButton botaoSalvar;
    private javax.swing.JTextField entradaId;
    private javax.swing.JLabel labelId;
    private javax.swing.JLabel labelMovimento;
    private javax.swing.JLabel labelNome;
    // End of variables declaration//GEN-END:variables
}
