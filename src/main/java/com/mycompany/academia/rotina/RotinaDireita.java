/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina;

import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.relatorio.Sublista;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Persistence;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author paulo
 */
public class RotinaDireita extends javax.swing.JPanel {

    ExercicioJpaController exercicioJpaController = new ExercicioJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));

    private final List<DefaultListModel<String>> model_lista = new ArrayList<>();
    private final List<Map> super_set_map = new ArrayList<>();
    // painelLista.getTabCount() fazia paineis terem nomes repetidos quando algum era removido
    // essa variavel resolve isso
    private int contagem_paineis = 0;

    /**
     * Creates new form RotinaDireita
     */
    public RotinaDireita() {
        initComponents();
        // TODO: Trocar lista por um JTree.
        adicionar_nova_lista.addActionListener((e) -> {
            JPanel container = new JPanel(new BorderLayout());

            JScrollPane painel = new JScrollPane();
            JList<String> lista = new JList<>();
            painel.setViewportView(lista);
            model_lista.add(new DefaultListModel<>());
            super_set_map.add(new HashMap<>());
            lista.setModel(model_lista.get(painel_lista.getTabCount()));

            // Alterar essa ordem quebra o actionListener em deletar_seleção
            // Linha 80; ((JPanel) painel).getComponent(0);
            container.add(painel, BorderLayout.CENTER);
            container.add(detalhes(), BorderLayout.SOUTH);

//            painel_lista.addTab("lista " + (contagem_paineis + 1), painel);
            painel_lista.addTab(Character.toString(65 + contagem_paineis), container);
            contagem_paineis += 1;
        });
        deletar_selecao.addActionListener((e) -> {
            int guia = painel_lista.getSelectedIndex();
            Component painel = painel_lista.getComponentAt(guia);
            Component container = ((JPanel) painel).getComponent(0);
            Component lista = ((JScrollPane) container).getViewport().getView();
            int indice_item_selecionado = ((JList) lista).getSelectedIndex();
            String valor_item_selecionado = ((JList) lista).getSelectedValue().toString();

            model_lista.get(guia).remove(indice_item_selecionado);
            super_set_map.get(guia).remove(valor_item_selecionado);
            // Tem um jeito de melhor de fazer isso?
            if (super_set_map.get(guia).containsValue(valor_item_selecionado)) {
                Set keys = super_set_map.get(guia).keySet();
                Object[] keys_array = keys.toArray();
                for (Object keys_array1 : keys_array) {
                    if (super_set_map.get(guia).get(keys_array1) == valor_item_selecionado) {
                        super_set_map.get(guia).remove(keys_array1);
                    }
                }
            }
        });
        // Deletar depois de trocar o JList por um JTree?
        adicionar_super_set.addActionListener((e) -> {
            int guia = painel_lista.getSelectedIndex();
            Component painel = painel_lista.getComponentAt(guia);
            Component container = ((JPanel) painel).getComponent(0);
            Component lista = ((JScrollPane) container).getViewport().getView();
            List indice_itens_selecionados = ((JList) lista).getSelectedValuesList();

            if (indice_itens_selecionados.size() == 2) {
                super_set_map.get(guia).put(indice_itens_selecionados.get(0), indice_itens_selecionados.get(1));
            } else {
                JOptionPane.showMessageDialog(container, "Selecione somente 2 exercicios");
            }
        });
        painel_lista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && e.getY() < 100) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem deletar = new JMenuItem(new AbstractAction("deletar") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int guia = painel_lista.getSelectedIndex();
                            // deleta a guia, o model e o HashMap
                            painel_lista.remove(guia);
                            model_lista.remove(guia);
                            super_set_map.remove(guia);
                        }
                    });
                    JMenuItem renomear = new JMenuItem(new AbstractAction("renomear") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int guia = painel_lista.getSelectedIndex();
                            String novo_nome = JOptionPane.showInputDialog("Novo Nome");
                            if (novo_nome != null && !novo_nome.isBlank()) {
                                painel_lista.setTitleAt(guia, novo_nome);
                            }
                        }
                    });
                    menu.add(renomear);
                    menu.add(deletar);
                    menu.show(painel_lista, e.getX(), e.getY());
                }
            }
        });
        imprimir.addActionListener((e) -> {
            int numero_de_abas = painel_lista.getTabCount();
            Collection<Sublista> relatorio_collection = new ArrayList<>();

            for (int aba_atual = 0; aba_atual < numero_de_abas; aba_atual++) {
                String titulo_aba = painel_lista.getTitleAt(aba_atual);
                List<Exercicio> lista_exercicios = new ArrayList<>();
                Map<Exercicio, Exercicio> map_super_set = new HashMap<>();

                for (int i = 0; i < model_lista.get(aba_atual).getSize(); i++) {
                    String busca = model_lista.get(aba_atual).get(i);
                    Exercicio exercicio_atual = exercicioJpaController.find_exercicios_where_nome_exercicio(busca);
                    lista_exercicios.add(exercicio_atual);
                }

                Object[] keys = super_set_map.get(aba_atual).keySet().toArray();
                Object[] values = super_set_map.get(aba_atual).values().toArray();
                for (int i = 0; i < super_set_map.get(aba_atual).size(); i++) {
                    Object key = exercicioJpaController.find_exercicios_where_nome_exercicio((String) keys[i]);
                    Object value = exercicioJpaController.find_exercicios_where_nome_exercicio((String) values[i]);
                    map_super_set.put((Exercicio) key, (Exercicio) value);
                }
                relatorio_collection.add(new Sublista(titulo_aba, lista_exercicios, map_super_set));
            }
        }
        );
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painel_lista = new javax.swing.JTabbedPane();
        adicionar_nova_lista = new javax.swing.JButton();
        deletar_selecao = new javax.swing.JButton();
        imprimir = new javax.swing.JButton();
        adicionar_super_set = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(819, 551));

        adicionar_nova_lista.setText("Nova Lista");

        deletar_selecao.setText("Remover Exercicio Selecionado");

        imprimir.setText("Imprimir");

        adicionar_super_set.setText("Adicionar seleção ao superset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painel_lista, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adicionar_nova_lista, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(deletar_selecao, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(adicionar_super_set, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(imprimir, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painel_lista, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(adicionar_nova_lista)
                .addGap(18, 18, 18)
                .addComponent(deletar_selecao)
                .addGap(18, 18, 18)
                .addComponent(adicionar_super_set)
                .addGap(18, 18, 18)
                .addComponent(imprimir)
                .addContainerGap(345, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adicionar_nova_lista;
    private javax.swing.JButton adicionar_super_set;
    private javax.swing.JButton deletar_selecao;
    private javax.swing.JButton imprimir;
    private javax.swing.JTabbedPane painel_lista;
    // End of variables declaration//GEN-END:variables

    // pensar em um nome melhor
    // TODO: Remover esse superset e superset_label daqui depois de refatorar o codigo pra usar um JTree ao inves do JList
    private JPanel detalhes() {
        JPanel detalhes = new JPanel(new GridLayout(4, 2));
        JLabel reps_label = new JLabel("Repetições: ");
        JLabel sets_label = new JLabel("Sets: ");
        JLabel descanco_label = new JLabel("Descanço: ");
        JLabel superset_label = new JLabel("Super set: ");
        JSpinner sets = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        JSpinner reps = new JSpinner(new SpinnerNumberModel(12, 1, 1000, 2));
        JSpinner descanco = new JSpinner(new SpinnerNumberModel(30, 0, 300, 5));
        descanco.setToolTipText("Tempo de descanço em segundos");
        JButton superset = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Exercicios");
                JScrollPane painel_rolagem = new JScrollPane();
                nodos(raiz);
                JTree arvore_super_sets = new JTree(raiz);

                painel_rolagem.setViewportView(arvore_super_sets);
                JOptionPane.showConfirmDialog(painel_rolagem,
                        painel_rolagem,
                        "Lista de super set",
                        JOptionPane.YES_OPTION,
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
        superset.setText("Visualizar");

        detalhes.add(reps_label);
        detalhes.add(reps);
        detalhes.add(sets_label);
        detalhes.add(sets);
        detalhes.add(descanco_label);
        detalhes.add(descanco);
        detalhes.add(superset_label);
        detalhes.add(superset);

        return detalhes;
    }

    public void nodos(DefaultMutableTreeNode tn) {
        int guia = painel_lista.getSelectedIndex();
        DefaultMutableTreeNode exercicio = null;
        DefaultMutableTreeNode exercicio_super_set = null;

        for (int i = 0; i < model_lista.get(guia).getSize(); i++) {
            String nome_exercicio = model_lista.get(guia).get(i);
            // sem esse if exercicios que foram adicionados como superset aparecem duas vezes na lista
            if (!super_set_map.get(guia).containsValue(nome_exercicio)) {
                exercicio = new DefaultMutableTreeNode(nome_exercicio);
                tn.add(exercicio);
                if (super_set_map.get(guia).keySet().contains(nome_exercicio)) {
                    exercicio_super_set = new DefaultMutableTreeNode(super_set_map.get(guia).get(nome_exercicio));
                    exercicio.add(exercicio_super_set);
                }
            }
        }
    }

    public JTabbedPane getPainel_lista() {
        return painel_lista;
    }

    public List<DefaultListModel<String>> getModel_lista() {
        return model_lista;
    }

    public List<Map> getSuper_set_map() {
        return super_set_map;
    }

}
