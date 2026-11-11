/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.exercise;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.mycompany.academia.model.entity.Exercicio;

/**
 *
 * @author paulo
 */
public class ExerciseRenderer extends JLabel implements ListCellRenderer<Object> {

    public ExerciseRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        String exerciseName;
        if (value != null) {
            exerciseName = ((Exercicio) value).getNomeExercicio();
        } else {
            exerciseName = "";
        }
        setText(exerciseName);
        return this;
    }

}
