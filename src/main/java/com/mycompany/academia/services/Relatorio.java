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
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
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

    public static void imprimirRelatorio(Collection<Sublista> lista) {
        try {
            JRBeanCollectionDataSource datasource = new JRBeanCollectionDataSource(lista);
            JasperReport relatorioPrincipal = loadReport(REPORT_PRINCIPAL);

            HashMap<String, Object> parametros = new HashMap<>();
            JasperReport relatorioSuperSet = loadReport(REPORT_SUPERSET);
            JasperReport relatorioExercicios = loadReport(REPORT_EXERCICIOS);
            parametros.put("subreportSuperset", relatorioSuperSet);
            parametros.put("subreportExercicios", relatorioExercicios);

            JasperPrint jasperPrint = JasperFillManager.fillReport(relatorioPrincipal, parametros, datasource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setTitle("Rotina");
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void imprimirRelatorio(ArrayList<String> titulos, ArrayList<ArrayList<ExercicioWrapper>> exercicios, ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superset) {
        Collection<Sublista> lista = asCollection(titulos, exercicios, superset);
        imprimirRelatorio(lista);
    }

    private static JasperReport loadReport(String report_location) throws JRException {
        InputStream inputStream = Relatorio.class.getResourceAsStream(report_location);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
        return jasperReport;
    }

    private static Collection<Sublista> asCollection(ArrayList<String> titulos, ArrayList<ArrayList<ExercicioWrapper>> exercicios, ArrayList<HashMap<ExercicioWrapper, ExercicioWrapper>> superset) {
        Collection<Sublista> lista = new ArrayList<>();

        for (int i = 0; i < exercicios.size(); i++) {
            HashMap<ExercicioWrapper, ExercicioWrapper> _hashmap = superset.get(i);
            ArrayList<ExercicioWrapper> _exercicios = new ArrayList<>(exercicios.get(i));
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
