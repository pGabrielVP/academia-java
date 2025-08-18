/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.view.musculoalvo.MusculoAlvoModel;
import java.util.List;

/**
 *
 * @author paulo
 */
public class MusculoAlvoControle {

    private final MusculoAlvoFacade musculoAlvoFacade;
    private final MusculoAlvoModel musculoAlvoModel;

    public MusculoAlvoControle() {
        musculoAlvoFacade = new MusculoAlvoFacade();
        musculoAlvoModel = new MusculoAlvoModel();
    }

    public List<MusculoAlvo> getListaMusculoAlvo() {
        return musculoAlvoFacade.listaTodos();
    }

    public MusculoAlvo buscar(Integer id) {
        return musculoAlvoFacade.buscar(id);
    }

    public void salvar(MusculoAlvo musculoAlvo) {
        musculoAlvoFacade.salvar(musculoAlvo);
        musculoAlvoModel.oferecer(musculoAlvo);
    }

    public void excluir(MusculoAlvo musculoAlvo) {
        musculoAlvoFacade.remover(musculoAlvo);
        musculoAlvoModel.deletar(musculoAlvo);
    }

    public void sincronizarMusculoAlvoModel() {
        musculoAlvoModel.sincronizar(getListaMusculoAlvo());
    }

    public MusculoAlvoModel getMusculoAlvoModel() {
        return musculoAlvoModel;
    }
}
