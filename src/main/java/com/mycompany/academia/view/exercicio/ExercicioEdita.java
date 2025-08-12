/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.view.exercicio;

import com.mycompany.academia.view.musculoalvo.MusculoAlvoRenderer;
import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.controle.MusculoAlvoJpaController;
import com.mycompany.academia.model.entidades.Exercicio;
import com.mycompany.academia.model.entidades.MusculoAlvo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author paulo
 */
public class ExercicioEdita extends javax.swing.JPanel {

    private final ExercicioJpaController controller;
    private final MusculoAlvoJpaController alvoJpaController;
    private final Object[] lista_musculo_alvo;
    private final Exercicio exercicio;
    private final JDialog dialog;
    private final ExercicioModel exercicioLista_modal;
    private File arquivo_selecionado;

    /**
     * Creates new form ExercicioEdita
     *
     * @param ex
     * @param jDialog
     * @param model
     */
    public ExercicioEdita(Exercicio ex, JDialog jDialog, ExercicioModel model) {
        controller = new ExercicioJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
        alvoJpaController = new MusculoAlvoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
        lista_musculo_alvo = alvoJpaController.findMusculoAlvoEntities().toArray();
        exercicio = ex;
        dialog = jDialog;
        exercicioLista_modal = model;

        initComponents();

        musculo_alvo.setModel(new DefaultComboBoxModel(lista_musculo_alvo));
        musculo_alvo.setRenderer(new MusculoAlvoRenderer());
        musculo_alvo.setSelectedItem(exercicio.getMusculoAlvo());

        if (exercicio.getImagem() != null) {
            nome_arquivo.setText("Imagem já selecionada");
        }

        selecionar_imagem.addActionListener((e) -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Permite somente imagens", "png"));

            if (e.getSource() == selecionar_imagem) {
                int valor_retorno = fc.showOpenDialog(this);
                if (valor_retorno == JFileChooser.APPROVE_OPTION) {
                    arquivo_selecionado = fc.getSelectedFile();
                    nome_arquivo.setText(arquivo_selecionado.getName());
                    nome_arquivo.setToolTipText(arquivo_selecionado.getPath());
                }
            }
        });

        salvar.addActionListener((e) -> {
            if (musculo_alvo.getSelectedItem() == null
                    || "".equals(nome_arquivo.getText())
                    || "".equals(nome_exercicio.getText())) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos");
                return;
            }
            int alvo_id = ((MusculoAlvo) musculo_alvo.getSelectedItem()).getIdAlvo();
            MusculoAlvo alvo = alvoJpaController.findMusculoAlvo(alvo_id);
            exercicio.setMusculoAlvo(alvo);
            exercicio.setNomeExercicio(nome_exercicio.getText());

//            definirImagem 
            if (exercicio.getIdExercicio() == null || arquivo_selecionado != null) {
                try {
                    BufferedImage imagem = ImageIO.read(arquivo_selecionado);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(imagem, "png", baos);
                    byte[] imgBytes = baos.toByteArray();
                    exercicio.setImagem(imgBytes);
                } catch (IOException ex1) {
                    Logger.getLogger(ExercicioEdita.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }

            if (exercicio.getIdExercicio() == null) {
                controller.create(exercicio);
                exercicioLista_modal.adicionarNovo(exercicio);
            } else {
                try {
                    controller.edit(exercicio);
                    exercicioLista_modal.atualizar(exercicio);
                } catch (Exception ex1) {
                    Logger.getLogger(ExercicioEdita.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            dialog.dispose();
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        id_exercicio = new javax.swing.JTextField();
        nome_exercicio = new javax.swing.JTextField();
        nome_arquivo = new javax.swing.JTextField();
        salvar = new javax.swing.JButton();
        musculo_alvo = new javax.swing.JComboBox<>();
        selecionar_imagem = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(300, 280));
        setPreferredSize(new java.awt.Dimension(340, 280));

        jLabel1.setText("ID");

        jLabel2.setText("Nome do Exercicio");

        jLabel3.setText("Imagem");

        jLabel4.setText("Musculo Alvo");

        id_exercicio.setEditable(false);
        id_exercicio.setColumns(8);
        if (exercicio.getIdExercicio()!= null) {
            id_exercicio.setText(exercicio.getIdExercicio().toString());
        }

        nome_exercicio.setColumns(8);
        if (exercicio.getNomeExercicio()!= null) {
            nome_exercicio.setText(exercicio.getNomeExercicio());
        }

        nome_arquivo.setEditable(false);
        nome_arquivo.setColumns(8);
        nome_arquivo.setToolTipText("caminho até a imagem selecionada");

        salvar.setText("Salvar");

        musculo_alvo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        selecionar_imagem.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salvar)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(81, 81, 81)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nome_arquivo)
                            .addComponent(nome_exercicio)
                            .addComponent(id_exercicio)
                            .addComponent(musculo_alvo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selecionar_imagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(id_exercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nome_exercicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(nome_arquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selecionar_imagem))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(musculo_alvo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addComponent(salvar)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField id_exercicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> musculo_alvo;
    private javax.swing.JTextField nome_arquivo;
    private javax.swing.JTextField nome_exercicio;
    private javax.swing.JButton salvar;
    private javax.swing.JButton selecionar_imagem;
    // End of variables declaration//GEN-END:variables
}
