/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.controle;

import com.mycompany.academia.facade.MusculoAlvoFacade;
import com.mycompany.academia.model.dto.ExercicioWrapper;
import com.mycompany.academia.model.entidades.MusculoAlvo;
import com.mycompany.academia.services.Relatorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author paulo
 */
public class RotinaControle {

    private final MusculoAlvoFacade musculoAlvoFacade;
    private final Relatorio relatorio;

    public RotinaControle(MusculoAlvoFacade musculoAlvoFacade, Relatorio relatorio) {
        this.musculoAlvoFacade = musculoAlvoFacade;
        this.relatorio = relatorio;
    }

    public List<MusculoAlvo> listaTodosMusculoAlvo() {
        return musculoAlvoFacade.listaTodos();
    }

    public void imprimirRelatorio(ArrayList<String> titulos, ArrayList<ArrayList<ExercicioWrapper>> exercicios, ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superset) {
        relatorio.imprimirRelatorio(titulos, exercicios, superset);
    }

}
