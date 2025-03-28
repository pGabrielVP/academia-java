/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.academia.rotina;

import com.mycompany.academia.controle.ExercicioJpaController;
import com.mycompany.academia.entidades.Exercicio;
import com.mycompany.academia.relatorio.ExercicioWrapper;
import com.mycompany.academia.relatorio.Relatorio;
import com.mycompany.academia.relatorio.Sublista;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import net.sf.jasperreports.engine.JRException;

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
    // superset_quantidade tem um int que serve de id para os superset criados para a aba
    private List<Integer> superset_quantidade = new ArrayList<>();
    // supserset_id2key key = id, value = super_set_map.key
    // supserset_id2key é usado para deletar um superset usando o botão remover exercicio
    private List<Map> supserset_id2key = new ArrayList<>();

    /**
     * Creates new form RotinaDireita
     */
    public RotinaDireita() {
        initComponents();
        adicionar_nova_lista.addActionListener((e) -> {
            JPanel container = new JPanel(new BorderLayout());

            JScrollPane painel = new JScrollPane();
            JList<String> lista = new JList<>();
            painel.setViewportView(lista);
            model_lista.add(new DefaultListModel<>());
            super_set_map.add(new HashMap<>());
            superset_quantidade.add(0);
            supserset_id2key.add(new HashMap<>());
            lista.setModel(model_lista.get(painel_lista.getTabCount()));

            // Alterar essa ordem quebra o actionListener em deletar_seleção 
            // Linha 94 & 120 & 174; ((JPanel) painel).getComponent(int);
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

            // "superset_INT" 
            // "superset_" é removido
            // int numero = INT
            if (valor_item_selecionado.contains("superset_")) {
                int numero = Integer.parseInt(model_lista.get(guia).get(indice_item_selecionado).substring(9));
                String key = (String) supserset_id2key.get(guia).get(numero);
                super_set_map.get(guia).remove(key);
            }
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
        adicionar_super_set.addActionListener((e) -> {
            int guia = painel_lista.getSelectedIndex();
            Component painel = painel_lista.getComponentAt(guia);
            Component container = ((JPanel) painel).getComponent(0);
            Component lista = ((JScrollPane) container).getViewport().getView();
            List valor_itens_selecionados = ((JList) lista).getSelectedValuesList();
            int[] indice_itens_selecionados = ((JList) lista).getSelectedIndices();

            if (valor_itens_selecionados.size() == 2) {
                int quantidade_atual = superset_quantidade.get(guia);
                if (valor_itens_selecionados.get(0).toString().contains("superset") || valor_itens_selecionados.get(1).toString().contains("superset")) {
                    JOptionPane.showMessageDialog(container, "Selecione somente exercicios");
                    return;
                }
                // adiciona os exercicios na lista de superset e remove da lista de exercicios 
                super_set_map.get(guia).put(valor_itens_selecionados.get(0), valor_itens_selecionados.get(1));
                model_lista.get(guia).remove(indice_itens_selecionados[1]);
                model_lista.get(guia).remove(indice_itens_selecionados[0]);
                model_lista.get(guia).addElement("superset_" + quantidade_atual);
                // guarda o key:value id:superset.key
                // aumenta a quantidade de superset criado na guia +1
                supserset_id2key.get(guia).put(quantidade_atual, valor_itens_selecionados.get(0));
                superset_quantidade.set(guia, quantidade_atual + 1);
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
                Component painel = painel_lista.getComponentAt(aba_atual);
                Component container_detalhes = ((JPanel) painel).getComponent(1);
                Component spinner_reps = ((JPanel) container_detalhes).getComponent(1);
                Component spinner_sets = ((JPanel) container_detalhes).getComponent(3);
                Component spinner_descanco = ((JPanel) container_detalhes).getComponent(5);
                int numero_reps = Integer.parseInt(((JSpinner) spinner_reps).getValue().toString());
                int numero_sets = Integer.parseInt(((JSpinner) spinner_sets).getValue().toString());
                int tempo_descanco = Integer.parseInt(((JSpinner) spinner_descanco).getValue().toString());

                String titulo_aba = painel_lista.getTitleAt(aba_atual);
                List<ExercicioWrapper> lista_exercicios = new ArrayList<>();
                List<ExercicioWrapper> lista_superset = new ArrayList<>();

                for (int i = 0; i < model_lista.get(aba_atual).getSize(); i++) {
                    String busca = model_lista.get(aba_atual).get(i);
                    // Adiciona na lista somente se o exercicio não for parte de um superset
                    if (!busca.contains("superset_")) {
                        Exercicio exercicio_atual = exercicioJpaController.find_exercicios_where_nome_exercicio(busca);
                        lista_exercicios.add(new ExercicioWrapper(exercicio_atual, numero_reps, numero_sets, tempo_descanco));
                    }
                }
                Object[] keys = super_set_map.get(aba_atual).keySet().toArray();
                Object[] values = super_set_map.get(aba_atual).values().toArray();
                for (int i = 0; i < super_set_map.get(aba_atual).size(); i++) {
                    Object key = exercicioJpaController.find_exercicios_where_nome_exercicio((String) keys[i]);
                    Object value = exercicioJpaController.find_exercicios_where_nome_exercicio((String) values[i]);

                    lista_superset.add(new ExercicioWrapper((Exercicio) key, numero_reps, numero_sets, tempo_descanco));
                    lista_superset.add(new ExercicioWrapper((Exercicio) value, numero_reps, numero_sets, tempo_descanco));
                }
                relatorio_collection.add(new Sublista(titulo_aba, lista_exercicios, lista_superset));
            }

            try {
                Relatorio.imprimirRelatorio(relatorio_collection);
            } catch (JRException | IOException ex) {
                Logger.getLogger(RotinaDireita.class.getName()).log(Level.SEVERE, null, ex);
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
            // Adiciona somente nome de exercicios, ignora superset
            if (!nome_exercicio.contains("superset_")) {
                exercicio = new DefaultMutableTreeNode(nome_exercicio);
                tn.add(exercicio);
            }
        }

        // superset são adicionados aqui
        Object[] keys = super_set_map.get(guia).keySet().toArray();
        Object[] values = super_set_map.get(guia).values().toArray();
        for (int i = 0; i < super_set_map.get(guia).size(); i++) {
            Object key = (String) keys[i];
            Object value = (String) values[i];

            exercicio_super_set = new DefaultMutableTreeNode("superset_" + i);
            exercicio_super_set.add(new DefaultMutableTreeNode(key));
            exercicio_super_set.add(new DefaultMutableTreeNode(value));

            tn.add(exercicio_super_set);
        }
    }

    public void adicionar_exercicio(String exr) {
        int guia = painel_lista.getSelectedIndex();
        model_lista.get(guia).addElement(exr);
    }

}
