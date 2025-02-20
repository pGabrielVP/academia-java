/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.academia;

import com.mycompany.academia.lista.ExercicioLista;
import com.mycompany.academia.lista.MusculoAlvoLista;
import com.mycompany.academia.rotina.Rotina;
import javax.swing.JInternalFrame;

/**
 *
 * @author paulo
 */
public class Academia extends javax.swing.JFrame {

    /**
     * Creates new form Academia
     */
    public Academia() {
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        novaRotinaMenu = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        ExercicioMenu = new javax.swing.JMenuItem();
        musculo_alvo_menu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1121, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
        );

        jDesktopPane1.setDragMode(jDesktopPane1.OUTLINE_DRAG_MODE);

        jMenu2.setText("Rotinas");

        novaRotinaMenu.setText("Novo");
        novaRotinaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novaRotinaMenuActionPerformed(evt);
            }
        });
        jMenu2.add(novaRotinaMenu);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Entidades");

        ExercicioMenu.setText("Exercicio");
        ExercicioMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExercicioMenuActionPerformed(evt);
            }
        });
        jMenu1.add(ExercicioMenu);

        musculo_alvo_menu.setText("Musculo Alvo");
        musculo_alvo_menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                musculo_alvo_menuActionPerformed(evt);
            }
        });
        jMenu1.add(musculo_alvo_menu);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void novaRotinaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novaRotinaMenuActionPerformed
        JInternalFrame rotina = new Rotina();
        rotina.setVisible(true);
        jDesktopPane1.add(rotina);
        jDesktopPane1.validate();
    }//GEN-LAST:event_novaRotinaMenuActionPerformed

    private void ExercicioMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExercicioMenuActionPerformed
        JInternalFrame listaExercicio = new ExercicioLista();
        listaExercicio.setVisible(true);

        jDesktopPane1.add(listaExercicio);
        jDesktopPane1.validate();
    }//GEN-LAST:event_ExercicioMenuActionPerformed

    private void musculo_alvo_menuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_musculo_alvo_menuActionPerformed
        JInternalFrame lista_musculo_alvo = new MusculoAlvoLista();
        lista_musculo_alvo.setVisible(true);
        
        jDesktopPane1.add(lista_musculo_alvo);
        jDesktopPane1.validate();
    }//GEN-LAST:event_musculo_alvo_menuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Academia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Academia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Academia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Academia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Academia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ExercicioMenu;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem musculo_alvo_menu;
    private javax.swing.JMenuItem novaRotinaMenu;
    // End of variables declaration//GEN-END:variables
}
