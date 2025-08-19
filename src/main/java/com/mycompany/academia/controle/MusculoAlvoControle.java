/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.view.musculoalvo.MusculoAlvoTableModel;
import java.util.List;

/**
 *
 * @author paulo
 */
public class MusculoAlvoControle {

    private final MusculoAlvoFacade musculoAlvoFacade;
    private final MusculoAlvoTableModel musculoAlvoTableModel;

    public MusculoAlvoControle() {
        musculoAlvoFacade = new MusculoAlvoFacade();
        musculoAlvoTableModel = new MusculoAlvoTableModel();
    }

    public List<MusculoAlvo> getListaMusculoAlvo() {
        return musculoAlvoFacade.listaTodos();
    }

    public MusculoAlvo buscar(Integer id) {
        return musculoAlvoFacade.buscar(id);
    }

    public void salvar(MusculoAlvo musculoAlvo) {
        musculoAlvoFacade.salvar(musculoAlvo);
        musculoAlvoTableModel.oferecer(musculoAlvo);
    }

    public void excluir(MusculoAlvo musculoAlvo) {
        musculoAlvoFacade.remover(musculoAlvo);
        musculoAlvoTableModel.deletar(musculoAlvo);
    }

    public void sincronizarMusculoAlvoTableModel() {
        musculoAlvoTableModel.sincronizar(getListaMusculoAlvo());
    }

    public MusculoAlvoTableModel getMusculoAlvoTableModel() {
        return musculoAlvoTableModel;
    }
}
