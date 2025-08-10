/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.academia.model.dto.ExercicioWrapper;
import com.mycompany.academia.model.dto.Sublista;
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
public class Relatorio {

    private static final String REPORT_PRINCIPAL = "/relatorios/Report_Principal.jasper";
    private static final String REPORT_SUPERSET = "/relatorios/Listar_Superset.jasper";
    private static final String REPORT_EXERCICIOS = "/relatorios/Listar_Exercicios.jasper";

    public static void imprimirRelatorio(Collection<Sublista> lista) throws JRException {
        JasperPrint jasperPrint = get_filled_report(lista);
        view_report(jasperPrint);
    }

    public static void imprimirRelatorio(ArrayList<String> titulos, ArrayList<ArrayList<ExercicioWrapper>> exercicios, ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superset) throws JRException {
        Collection<Sublista> lista = get_dataset(titulos, exercicios, superset);
        JasperPrint jasperPrint = get_filled_report(lista);
        view_report(jasperPrint);
    }

    private static JasperPrint get_filled_report(Collection<Sublista> lista) throws JRException {
        JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(lista);
        JasperReport principal_jasper = get_report_object(REPORT_PRINCIPAL);
        HashMap<String, Object> parametros = set_parametros();
        JasperPrint jasperPrint = JasperFillManager.fillReport(principal_jasper, parametros, datasource);
        return jasperPrint;
    }

    private static HashMap<String, Object> set_parametros() throws JRException {
        HashMap<String, Object> parametros = new HashMap();
        JasperReport superset_jasper = get_report_object(REPORT_SUPERSET);
        JasperReport exercicios_jasper = get_report_object(REPORT_EXERCICIOS);
        parametros.put("subreportSuperset", superset_jasper);
        parametros.put("subreportExercicios", exercicios_jasper);
        return parametros;
    }

    private static JasperReport get_report_object(String report_location) throws JRException {
        InputStream report_input_stream = Relatorio.class.getResourceAsStream(report_location);
        JasperReport report_object = (JasperReport) JRLoader.loadObject(report_input_stream);
        return report_object;
    }

    private static void view_report(JasperPrint jasperPrint) {
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        jasperViewer.setTitle("ROTINA");
        jasperViewer.setVisible(true);
    }

    private static void export_to_pdf(JasperPrint jasperPrint) throws JRException {
        JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private static Collection<Sublista> get_dataset(ArrayList<String> titulos, ArrayList<ArrayList<ExercicioWrapper>> exercicios, ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superset) {
        Collection<Sublista> lista = new ArrayList<>();

        for (int i = 0; i < exercicios.size(); i++) {
            HashMap<ExercicioWrapper, ExercicioWrapper> _hashmap = superset.get(i);
            ArrayList<ExercicioWrapper> _exercicios = (ArrayList<ExercicioWrapper>) exercicios.get(i).clone();
            ArrayList<ExercicioWrapper> _superset = new ArrayList<>();
            for (Map.Entry<ExercicioWrapper, ExercicioWrapper> ex_wpr : _hashmap.entrySet()) {
                if (!_superset.contains(ex_wpr.getKey())) {
                    _superset.add(ex_wpr.getKey());
                    _superset.add(_hashmap.get(ex_wpr.getKey()));
                    _exercicios.remove(ex_wpr.getKey());
                    _exercicios.remove(_hashmap.get(ex_wpr.getKey()));
                }
            }
            lista.add(new Sublista(titulos.get(i), _exercicios, _superset));
        }
        return lista;
    }
}
