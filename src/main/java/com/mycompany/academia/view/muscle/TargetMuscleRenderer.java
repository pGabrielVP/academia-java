/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.view.muscle;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.mycompany.academia.model.entity.MusculoAlvo;

/**
 *
 * @author paulo
 */
public class TargetMuscleRenderer extends JLabel implements ListCellRenderer<Object> {

    public TargetMuscleRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String nome;

        if (value != null) {
            nome = ((MusculoAlvo) value).getNomeAlvo();
        } else {
            nome = "";
        }

        setText(nome);

        return this;
    }

}
