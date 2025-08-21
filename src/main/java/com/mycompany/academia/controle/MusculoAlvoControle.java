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

    public MusculoAlvoControle(MusculoAlvoFacade musculoAlvoFacade, MusculoAlvoTableModel musculoAlvoTableModel) {
        this.musculoAlvoFacade = musculoAlvoFacade;
        this.musculoAlvoTableModel = musculoAlvoTableModel;
    }

    public void sincronizarMusculoAlvoTableModel() {
        musculoAlvoTableModel.sincronizar(getListaMusculoAlvo());
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

    public MusculoAlvoTableModel getMusculoAlvoTableModel() {
        return musculoAlvoTableModel;
    }

}
