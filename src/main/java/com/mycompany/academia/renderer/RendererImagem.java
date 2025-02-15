/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.renderer;

import com.mycompany.academia.entidades.Imagem;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author paulo
 */
public class RendererImagem extends JLabel implements ListCellRenderer<Object> {

    public RendererImagem() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String nome;
        ImageIcon icon;

        if (value != null) {
            icon = new ImageIcon(((Imagem) value).getImagem());
            nome = ((Imagem) value).getDescricao();
        } else {
            icon = null; // TODO: Imagem de erro
            nome = null;
        }

        setIcon(icon);
        setText(nome);

        return this;
    }
}
