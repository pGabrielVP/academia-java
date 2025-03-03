/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.relatorio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author paulo
 */
public class Relatorio {

    public static void imprimirRelatorio(Collection<Sublista> lista) throws JRException, FileNotFoundException, IOException {
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(lista);
        JasperReport jasperReport = JasperCompileManager.compileReport("Report_Principal.jrxml");
        JasperReport subreportSuperset = JasperCompileManager.compileReport("Listar_Superset.jrxml");
        JasperReport subreportExercicios = JasperCompileManager.compileReport("Listar_Exercicios.jrxml");

        Map parametros = new HashMap<String, Object>();
        parametros.put("subreportSuperset", subreportSuperset);
        parametros.put("subreportExercicios", subreportExercicios);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, datasource);
//        JasperExportManager.exportReportToPdf(jasperPrint);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setTitle("ROTINA");
        jasperViewer.setVisible(true);
    }
}
