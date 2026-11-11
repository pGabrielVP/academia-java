/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controller;

import com.mycompany.academia.facade.TargetMuscleFacade;
import com.mycompany.academia.model.dto.Sublista;
import com.mycompany.academia.model.entity.MusculoAlvo;
import com.mycompany.academia.service.Report;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author paulo
 */
public class RoutineController {

    private final TargetMuscleFacade musculoAlvoFacade;
    private final Report relatorio;

    public RoutineController(TargetMuscleFacade musculoAlvoFacade, Report relatorio) {
        this.musculoAlvoFacade = musculoAlvoFacade;
        this.relatorio = relatorio;
    }

    public List<MusculoAlvo> listaTodosMusculoAlvo() {
        return musculoAlvoFacade.listaTodos();
    }

    public void imprimirRelatorio(Collection<Sublista> lista) {
        relatorio.verRelatorio(lista);
    }

}
