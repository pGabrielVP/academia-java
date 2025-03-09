/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author paulo
 */
public class FrameTeste extends JFrame {

    public FrameTeste() throws HeadlessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(new RotinaDireita__JTree());
        pack();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FrameTeste().setVisible(true);
        });
    }

}
