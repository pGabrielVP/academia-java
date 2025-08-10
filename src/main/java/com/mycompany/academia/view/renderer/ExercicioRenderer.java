/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.renderer;

import com.mycompany.academia.model.entidades.Exercicio;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author paulo
 */
public class ExercicioRenderer extends JLabel implements ListCellRenderer<Object> {

    public ExercicioRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String nome;

        if (value != null) {
            nome = ((Exercicio) value).getNomeExercicio();
        } else {
            nome = "";
        }

        setText(nome);

        return this;
    }

}
