/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author paulo
 */
public class RotinaDireita__JTree extends JPanel {

    public RotinaDireita__JTree() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BorderLayout());

        box__menu_lateral();
//        grid__menu_lateral();
        adicionar_action_listener();

        add(painel_menu_lateral, BorderLayout.LINE_END);
        add(painel_view_jTree, BorderLayout.CENTER);

        painel_menu_lateral.setBackground(Color.red);
    }

    // painéis
    private final JPanel painel_menu_lateral = new JPanel();
    private final JTabbedPane painel_view_jTree = new JTabbedPane();

    // botões
    private final JButton adicionar_nova_aba = new JButton("Adicionar nova aba");
    private final JButton renomear_aba = new JButton("Renomear aba");
    private final JButton deletar_aba = new JButton("Deletar aba");
    private final JButton criar_superset = new JButton("Criar superset");
    private final JButton editar_exercicio = new JButton("Editar exercicio");
    private final JButton deletar_seleção = new JButton("Deletar seleção");
    private final JButton imprimir = new JButton("Imprimir");
    private final JButton TESTE_ADICIONAR_EXERCICIO = new JButton("add novo");

    // extras
    private final Dimension tamanho_botao = new Dimension(142, 26);
    private int numero_total_abas = 0;
    private final List<DefaultTreeModel> tree_model_lista = new ArrayList<>();

    private void adicionar_action_listener() {
        adicionar_nova_aba.addActionListener((e) -> {
            String nome_aba = Character.toString(65 + numero_total_abas);

            painel_view_jTree.addTab(nome_aba, novo_JTree(nome_aba));
            numero_total_abas++;
        });
        TESTE_ADICIONAR_EXERCICIO.addActionListener((e) -> {
            inserir_exercicio("Exercicio");
        });
        criar_superset.addActionListener((e) -> {
            inserir_superset();
        });
    }

    private void adicionar_repSetDesc(DefaultMutableTreeNode nodo) {
        DefaultMutableTreeNode nodo_sets = new DefaultMutableTreeNode("sets");
        DefaultMutableTreeNode nodo_reps = new DefaultMutableTreeNode("reps");
        DefaultMutableTreeNode nodo_descanco = new DefaultMutableTreeNode("descanço");

        nodo_sets.add(new DefaultMutableTreeNode(3));
        nodo_reps.add(new DefaultMutableTreeNode(12));
        nodo_descanco.add(new DefaultMutableTreeNode(30));

        nodo.add(nodo_sets);
        nodo.add(nodo_reps);
        nodo.add(nodo_descanco);
    }

    private void inserir_superset() {
        int guia_atual = painel_view_jTree.getSelectedIndex();
        DefaultTreeModel model = tree_model_lista.get(guia_atual);
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) model.getRoot();
        // get selected_nodes
        JScrollPane painel_rolagem = (JScrollPane) painel_view_jTree.getComponentAt(guia_atual);
        JTree jTree = (JTree) painel_rolagem.getViewport().getView();
        if (jTree.getSelectionCount() != 2) {
            return;
        }

        TreePath[] selected_nodes = jTree.getSelectionPaths();
        if (selected_nodes[0].getPathCount() != 2 || selected_nodes[1].getPathCount() != 2) {
            return;
        }

        Object exercicio_1 = selected_nodes[0].getLastPathComponent();
        Object exercicio_2 = selected_nodes[1].getLastPathComponent();
        if (exercicio_1.toString().contains("superset_") || exercicio_2.toString().contains("superset_")) {
            return;
        }

        DefaultMutableTreeNode superset = new DefaultMutableTreeNode("superset_" + 1);
        superset.add(new DefaultMutableTreeNode(exercicio_1));
        superset.add(new DefaultMutableTreeNode(exercicio_2));
        model.removeNodeFromParent((DefaultMutableTreeNode) exercicio_1);
        model.removeNodeFromParent((DefaultMutableTreeNode) exercicio_2);

        adicionar_repSetDesc(superset);
        model.insertNodeInto(superset, nodo, nodo.getChildCount());
    }

    private void inserir_exercicio(String exercicio) {
        int guia_atual = painel_view_jTree.getSelectedIndex();
        DefaultTreeModel model = tree_model_lista.get(guia_atual);
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) model.getRoot();
        String nome_exercicio_adicionar = exercicio + " " + (int) (Math.random() * 100); // Isso aqui não é necessario; Apagar depois

        // check se o exercicio já existe na arvore
        Enumeration<TreeNode> child_nodes = nodo.children();
        while (child_nodes.hasMoreElements()) {
            DefaultMutableTreeNode nodo_alvo = (DefaultMutableTreeNode) child_nodes.nextElement();
            Object objeto_nodo_alvo = nodo_alvo.getUserObject();
            // <editor-fold defaultstate="collapsed" desc="if (objeto_nodo_alvo.contains("superset_"))">
            if (objeto_nodo_alvo.toString().contains("superset_")) {
                Enumeration<TreeNode> superset_child_nodes = nodo_alvo.children();
                while (superset_child_nodes.hasMoreElements()) {
                    DefaultMutableTreeNode superset_alvo = (DefaultMutableTreeNode) superset_child_nodes.nextElement();
                    Object objeto_superset_alvo = superset_alvo.getUserObject();

                    if (objeto_superset_alvo.toString().contentEquals(nome_exercicio_adicionar)) {
                        System.out.println("Exercicio Repetido: " + nome_exercicio_adicionar);
                        return;
                    }
                }
            } // </editor-fold>
            if (objeto_nodo_alvo.toString().contentEquals(nome_exercicio_adicionar)) {
                System.out.println("Exercicio Repetido: " + nome_exercicio_adicionar);
                return;
            }
        }

        // adiciona exercicio na arvore
        DefaultMutableTreeNode nodo_exercicio = new DefaultMutableTreeNode(nome_exercicio_adicionar);
        adicionar_repSetDesc(nodo_exercicio);
        model.insertNodeInto(nodo_exercicio, nodo, nodo.getChildCount());
    }

    private JScrollPane novo_JTree(String nome_aba) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(nome_aba);
        JTree arvore = new JTree(root);
        tree_model_lista.add((DefaultTreeModel) arvore.getModel());

        JScrollPane painel_rolagem = new JScrollPane();
        painel_rolagem.setViewportView(arvore);

        return painel_rolagem;
    }

    private JPanel box__menu_lateral() {
        Dimension tamanho_menu = new Dimension((int) tamanho_botao.getWidth() + 32, (int) tamanho_botao.getHeight());

        painel_menu_lateral.setLayout(new BoxLayout(painel_menu_lateral, BoxLayout.PAGE_AXIS));
        painel_menu_lateral.setMinimumSize(tamanho_menu);
        painel_menu_lateral.setPreferredSize(tamanho_menu);

        adicionar_nova_aba.setAlignmentX(CENTER_ALIGNMENT);
        renomear_aba.setAlignmentX(CENTER_ALIGNMENT);
        deletar_aba.setAlignmentX(CENTER_ALIGNMENT);
        criar_superset.setAlignmentX(CENTER_ALIGNMENT);
        editar_exercicio.setAlignmentX(CENTER_ALIGNMENT);
        deletar_seleção.setAlignmentX(CENTER_ALIGNMENT);
        imprimir.setAlignmentX(CENTER_ALIGNMENT);
        TESTE_ADICIONAR_EXERCICIO.setAlignmentX(CENTER_ALIGNMENT);

        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 100)));
        painel_menu_lateral.add(adicionar_nova_aba);
        painel_menu_lateral.add(renomear_aba);
        painel_menu_lateral.add(deletar_aba);
        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 40)));
        painel_menu_lateral.add(criar_superset);
        painel_menu_lateral.add(editar_exercicio);
        painel_menu_lateral.add(deletar_seleção);
        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 40)));
        painel_menu_lateral.add(imprimir);
        painel_menu_lateral.add(TESTE_ADICIONAR_EXERCICIO);

        return painel_menu_lateral;
    }

    private JPanel grid__menu_lateral() {
        Dimension tamanho_menu = new Dimension((int) tamanho_botao.getWidth() + 32, (int) tamanho_botao.getHeight());

        JPanel grupo_botoes_aba = new JPanel(new GridLayout(3, 1, 0, 10));
        JPanel grupo_botoes_exercicio = new JPanel(new GridLayout(3, 1, 0, 10));
        JPanel grupo_botao_imprimir = new JPanel(new GridLayout(1, 1));

        grupo_botoes_aba.setMaximumSize(new Dimension((int) tamanho_botao.getWidth(), (int) (tamanho_botao.getHeight() * 3) + 30));
        grupo_botoes_exercicio.setMaximumSize(new Dimension((int) tamanho_botao.getWidth(), (int) (tamanho_botao.getHeight() * 3) + 30));
        grupo_botao_imprimir.setMaximumSize(tamanho_botao);

        painel_menu_lateral.setLayout(new BoxLayout(painel_menu_lateral, BoxLayout.PAGE_AXIS));
        painel_menu_lateral.setMinimumSize(tamanho_menu);
        painel_menu_lateral.setPreferredSize(tamanho_menu);

        adicionar_nova_aba.setAlignmentX(CENTER_ALIGNMENT);
        renomear_aba.setAlignmentX(CENTER_ALIGNMENT);
        deletar_aba.setAlignmentX(CENTER_ALIGNMENT);
        criar_superset.setAlignmentX(CENTER_ALIGNMENT);
        editar_exercicio.setAlignmentX(CENTER_ALIGNMENT);
        deletar_seleção.setAlignmentX(CENTER_ALIGNMENT);
        imprimir.setAlignmentX(CENTER_ALIGNMENT);

        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 100)));
        grupo_botoes_aba.add(adicionar_nova_aba);
        grupo_botoes_aba.add(renomear_aba);
        grupo_botoes_aba.add(deletar_aba);
        painel_menu_lateral.add(grupo_botoes_aba);
        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 40)));
        grupo_botoes_exercicio.add(criar_superset);
        grupo_botoes_exercicio.add(editar_exercicio);
        grupo_botoes_exercicio.add(deletar_seleção);
        painel_menu_lateral.add(grupo_botoes_exercicio);
        painel_menu_lateral.add(Box.createRigidArea(new Dimension(0, 40)));
        grupo_botao_imprimir.add(imprimir);
        painel_menu_lateral.add(grupo_botao_imprimir);

        return painel_menu_lateral;
    }

}
