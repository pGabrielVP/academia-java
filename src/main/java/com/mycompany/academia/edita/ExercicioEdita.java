/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.edita;

import com.mycompany.academia.controle.EquipamentoJpaController;
import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.controle.MusculoAlvoJpaController;
import com.mycompany.academia.entidades.Equipamento;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.entidades.MusculoAlvo;
import com.mycompany.academia.lista.ExercicioModel;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final EquipamentoJpaController equipamentoJpaController;
    private final Object[] lista_musculo_alvo;
    private final Object[] lista_equipamento_necessario;
    private final Exercicio exercicio;
    private final JDialog dialog;
    private final ExercicioModel exercicioLista_modal;
    private File imagem;

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
        equipamentoJpaController = new EquipamentoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
        lista_musculo_alvo = alvoJpaController.findMusculoAlvoEntities().toArray();
        lista_equipamento_necessario = equipamentoJpaController.findEquipamentoEntities().toArray();
        exercicio = ex;
        dialog = jDialog;
        exercicioLista_modal = model;
        initComponents();

        musculo_alvo.setModel(new DefaultComboBoxModel(lista_musculo_alvo));
        musculo_alvo.setRenderer(new MusculoAlvoRenderer());
        musculo_alvo.setSelectedItem(exercicio.getMusculoAlvo());

        equipamento_necessario.setModel(new DefaultComboBoxModel(lista_equipamento_necessario));
        equipamento_necessario.setRenderer(new EquipamentoNecessarioRenderer());
        equipamento_necessario.setSelectedItem(exercicio.getEquipamentoNecessario());

        selecionar_imagem.addActionListener((e) -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Permite somente imagens", "png"));

            if (e.getSource() == selecionar_imagem) {
                int valor_retorno = fc.showOpenDialog(this);
                if (valor_retorno == JFileChooser.APPROVE_OPTION) {
                    imagem = fc.getSelectedFile();
                    nome_arquivo.setText(imagem.getName());
                    nome_arquivo.setToolTipText(imagem.getPath());
                }
            }
        });

        salvar.addActionListener((e) -> {
            if (musculo_alvo.getSelectedItem() == null
                    || equipamento_necessario.getSelectedItem() == null
                    || "".equals(nome_arquivo.getText())
                    || "".equals(nome_exercicio.getText())) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos");
                return;
            }
            int alvo_id = ((MusculoAlvo) musculo_alvo.getSelectedItem()).getIdAlvo();
            MusculoAlvo alvo = alvoJpaController.findMusculoAlvo(alvo_id);
            int equipamento_id = ((Equipamento) equipamento_necessario.getSelectedItem()).getIdEquipamento();
            Equipamento equipamento = equipamentoJpaController.findEquipamento(equipamento_id);
            exercicio.setMusculoAlvo(alvo);
            exercicio.setEquipamentoNecessario(equipamento);
            exercicio.setImagem(nome_arquivo.getToolTipText());
            exercicio.setNomeExercicio(nome_exercicio.getText());

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
        jLabel5 = new javax.swing.JLabel();
        equipamento_necessario = new javax.swing.JComboBox<>();

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
        if (exercicio.getImagem()!= null) {
            nome_arquivo.setText(exercicio.getImagem());
            nome_arquivo.setToolTipText(exercicio.getImagem());
        }

        salvar.setText("Salvar");

        musculo_alvo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        selecionar_imagem.setText("...");

        jLabel5.setText("Equipamento necessario");

        equipamento_necessario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nome_arquivo)
                            .addComponent(nome_exercicio)
                            .addComponent(id_exercicio)
                            .addComponent(musculo_alvo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(equipamento_necessario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(equipamento_necessario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(salvar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> equipamento_necessario;
    private javax.swing.JTextField id_exercicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox<String> musculo_alvo;
    private javax.swing.JTextField nome_arquivo;
    private javax.swing.JTextField nome_exercicio;
    private javax.swing.JButton salvar;
    private javax.swing.JButton selecionar_imagem;
    // End of variables declaration//GEN-END:variables
}
