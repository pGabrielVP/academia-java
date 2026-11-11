/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import com.mycompany.academia.model.dto.Sublista;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author paulo
 */
public class Report {

    private static final String MAIN_REPORT = "/relatorios/Report_Principal.jasper";
    private static final String SUPERSET_REPORT = "/relatorios/Listar_Superset.jasper";
    private static final String EXERCISES_REPORT = "/relatorios/Listar_Exercicios.jasper";

    public void viewReport(Collection<Sublista> list) {
        JasperPrint jasperPrint = fillReport(list);
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setTitle("Rotina");
        jasperViewer.setVisible(true);
    }

    public byte[] exportPdf(Collection<Sublista> list) {
        byte[] report = {};
        try {
            JasperPrint jasperPrint = fillReport(list);
            report = JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return report;
    }

    private JasperPrint fillReport(Collection<Sublista> list) {
        JasperPrint jasperPrint = new JasperPrint();
        try {
            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(list);
            JasperReport mainReport = loadReport(MAIN_REPORT);

            HashMap<String, Object> parameters = new HashMap<>();
            JasperReport supersetReport = loadReport(SUPERSET_REPORT);
            JasperReport exercisesReport = loadReport(EXERCISES_REPORT);
            parameters.put("subreportSuperset", supersetReport);
            parameters.put("subreportExercicios", exercisesReport);
            jasperPrint = JasperFillManager.fillReport(mainReport, parameters, datasource);
        } catch (JRException ex) {
            Logger.getLogger(Report.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jasperPrint;
    }

    private JasperReport loadReport(String report_location) throws JRException {
        InputStream inputStream = Report.class.getResourceAsStream(report_location);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
        return jasperReport;
    }

}
