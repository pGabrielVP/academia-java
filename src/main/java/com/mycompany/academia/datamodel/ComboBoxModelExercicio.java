/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.datamodel;

import com.mycompany.academia.controle.MovimentoJpaController;
import com.mycompany.academia.entidades.Movimento;
import java.util.List;
import javax.persistence.Persistence;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

/**
 *
 * @author paulo
 */
// TODO: Deletar essa classe.
// TODO: Atualizar a classe ExercicioEdita.
public class ComboBoxModelExercicio extends AbstractListModel<Object> implements MutableComboBoxModel<Object> {

    MovimentoJpaController movimentoJpaController = new MovimentoJpaController(Persistence.createEntityManagerFactory("com.mycompany_academia_jar_1PU"));
    List<Movimento> lista_movimento;

    Object selectedObject;

    public ComboBoxModelExercicio() {
        lista_movimento = movimentoJpaController.findMovimentoEntities();
    }

    @Override
    public int getSize() {
        return movimentoJpaController.getMovimentoCount();
    }

    @Override
    public Object getElementAt(int index) {
        if (index >= 0 && index < lista_movimento.size()) {
            return lista_movimento.get(index);
        } else {
            return null;
        }
    }

    @Override
    public void addElement(Object item) {
        lista_movimento.add((Movimento) item);
        fireIntervalAdded(this, lista_movimento.size() - 1, lista_movimento.size() - 1);
        if (lista_movimento.size() == 1 && selectedObject == null && item != null) {
            setSelectedItem(item);
        }
    }

    @Override
    public void removeElement(Object obj) {
        int index = lista_movimento.indexOf(obj);
        if (index != -1) {
            removeElementAt(index);
        }
    }

    @Override
    public void insertElementAt(Object item, int index) {
        lista_movimento.add(index, (Movimento) item);
        fireIntervalAdded(this, index, index);
    }

    @Override
    public void removeElementAt(int index) {
        if (getElementAt(index) == selectedObject) {
            if (index == 0) {
                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
            } else {
                setSelectedItem(getElementAt(index - 1));
            }
        }

        lista_movimento.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        if ((selectedObject != null && !selectedObject.equals(anItem))
                || selectedObject == null && anItem != null) {
            selectedObject = anItem;
            fireContentsChanged(this, -1, -1);
        }
    }

    @Override
    public Object getSelectedItem() {
        return selectedObject;
    }

}
