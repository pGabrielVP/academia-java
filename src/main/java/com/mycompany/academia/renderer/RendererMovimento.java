/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.renderer;

import com.mycompany.academia.entidades.Movimento;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author paulo
 */
public class RendererMovimento extends JLabel implements ListCellRenderer<Object> {

    public RendererMovimento() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        ImageIcon antes;
        ImageIcon depois;

        if (value != null) {
            antes = new ImageIcon(((Movimento) value).getAntes().getImagem());
            depois = new ImageIcon(((Movimento) value).getAntes().getImagem());
        } else {
            antes = null;
            depois = null;
        }
        // TODO: arrumar isso.
        setIcon(antes);
        setIcon(depois);

        return this;
    }
}
