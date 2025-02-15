/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.academia.lista;

import com.mycompany.academia.controle.MovimentoJpaController;
import com.mycompany.academia.controle.exceptions.IllegalOrphanException;
import com.mycompany.academia.controle.exceptions.NonexistentEntityException;
import com.mycompany.academia.datamodel.TableModelMovimento;
import com.mycompany.academia.edita.MovimentoEdita;
import com.mycompany.academia.entidades.Movimento;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;
import javax.swing.JDialog;

/**
 *
 * @author paulo
 */
public class ListaMovimento extends javax.swing.JInternalFrame {

    private final MovimentoJpaController controller = new MovimentoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));

    /**
     * Creates new form ModeloCrud
     */
    public ListaMovimento() {
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

        jScrollPane2 = new javax.swing.JScrollPane();
        ListaEntidades = new javax.swing.JTable();
        CriarEntidade = new javax.swing.JButton();
        DeletarEntidade = new javax.swing.JButton();
        EditarEntidade = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Lista Movimento");
        setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        setPreferredSize(new java.awt.Dimension(919, 565));

        ListaEntidades.setModel(new com.mycompany.academia.datamodel.TableModelMovimento());
        jScrollPane2.setViewportView(ListaEntidades);

        CriarEntidade.setText("Criar");
        CriarEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CriarEntidadeActionPerformed(evt);
            }
        });

        DeletarEntidade.setText("Deletar");
        DeletarEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeletarEntidadeActionPerformed(evt);
            }
        });

        EditarEntidade.setText("Editar");
        EditarEntidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarEntidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DeletarEntidade)
                    .addComponent(CriarEntidade)
                    .addComponent(EditarEntidade))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(CriarEntidade)
                .addGap(18, 18, 18)
                .addComponent(DeletarEntidade)
                .addGap(18, 18, 18)
                .addComponent(EditarEntidade)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 517, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CriarEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CriarEntidadeActionPerformed
        JDialog jDialog = new JDialog();
        MovimentoEdita entrada = new MovimentoEdita(new Movimento(), jDialog);
        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jDialog.add(entrada);
        jDialog.setSize(entrada.getPreferredSize());

        jDialog.validate();
        jDialog.setModal(true);
        jDialog.setVisible(true);
        // TODO: atualizar a lista sempre que uma imagem nova for adicionada
    }//GEN-LAST:event_CriarEntidadeActionPerformed

    private void EditarEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarEntidadeActionPerformed
        JDialog jDialog = new JDialog();
        // procura o objeto no banco de dados para editar
        Movimento editando = controller.findMovimento(Integer.valueOf(ListaEntidades.getValueAt(ListaEntidades.getSelectedRow(), 0).toString()));
        MovimentoEdita entrada = new MovimentoEdita(editando, jDialog);

        jDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        jDialog.add(entrada);
        jDialog.setSize(entrada.getPreferredSize());

        jDialog.validate();
        jDialog.setModal(true);
        jDialog.setVisible(true);

        // TODO: Atualizar a lista depois de editar uma entidade
    }//GEN-LAST:event_EditarEntidadeActionPerformed

    private void DeletarEntidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeletarEntidadeActionPerformed
        try {
            // retorna um integer com o valor da coluna 0 (id) na linha selecionada
            // deleta a entidade no banco de dados
            controller.destroy(Integer.valueOf(ListaEntidades.getValueAt(ListaEntidades.getSelectedRow(), 0).toString()));
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(ListaMovimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        // atualiza lista depois de deletar uma entidade
        ListaEntidades.setModel(new TableModelMovimento());
    }//GEN-LAST:event_DeletarEntidadeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CriarEntidade;
    private javax.swing.JButton DeletarEntidade;
    private javax.swing.JButton EditarEntidade;
    private javax.swing.JTable ListaEntidades;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
