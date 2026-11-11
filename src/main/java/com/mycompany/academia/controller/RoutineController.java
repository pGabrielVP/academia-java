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

    private final TargetMuscleFacade targetMuscleFacade;
    private final Report report;

    public RoutineController(TargetMuscleFacade targetMuscleFacade, Report report) {
        this.targetMuscleFacade = targetMuscleFacade;
        this.report = report;
    }

    public List<MusculoAlvo> listAllTargetMuscle() {
        return targetMuscleFacade.listAll();
    }

    public void printReport(Collection<Sublista> list) {
        report.viewReport(list);
    }

}
