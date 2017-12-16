package test.java;

import main.java.com.decisionSupportSystem.logic.Alternative;
import main.java.com.decisionSupportSystem.logic.AnalyticHierarchyProcess;
import main.java.com.decisionSupportSystem.logic.Criteria;
import main.java.com.decisionSupportSystem.logic.DataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TestAnalyticHierarchyProcess {
    public static void main(String[] args) {
        DataModel dm = new DataModel();

        // create alternatives
        ArrayList<Alternative> alternatives = new ArrayList<>();
        alternatives.add(new Alternative("A"));
        alternatives.add(new Alternative("B"));
        alternatives.add(new Alternative("C"));
        dm.setAlternatives(alternatives);

        // create criterias
        Criteria c1 = new Criteria("Хар-ки магазина");
        dm.addCriteria(c1);
        Criteria c2 = new Criteria("Внешний вид");
        dm.addCriteria(c2);
        Criteria c3 = new Criteria("Техн. хар-ки");
        dm.addCriteria(c3);

        // create criteria rates
        double[][] criteriaRates = {
                { 1.0, 5.0, 7.0 },
                { 0.2, 1.0, 2.0 },
                { 0.14, 0.50, 1.0 }
        };
        dm.setCriteriasRates(criteriaRates);

        // create subcriterias
        Criteria c1s1 = new Criteria("Скорость доставки", c1);
        Criteria c1s2 = new Criteria("Престиж", c1);

        dm.addSubcriteria(c1, c1s1);
        dm.addSubcriteria(c1, c1s2);

        Criteria c2s1 = new Criteria("Цвет", c2);
        Criteria c2s2 = new Criteria("Расположение конфорок", c2);
        dm.addSubcriteria(c2, c2s1);
        dm.addSubcriteria(c2, c2s2);

        Criteria c3s1 = new Criteria("Режимы работы");
        Criteria c3s2 = new Criteria("Объем шкафа");
        dm.addSubcriteria(c3, c3s1);
        dm.addSubcriteria(c3, c3s2);

        // create subcriteria rates
        HashMap<Criteria, double[][]> subcriteriaRates = new HashMap<>();
        double[][] c1subs = {
                {1.0, 0.2},
                {5.0, 1.0}
        };
        subcriteriaRates.put(c1, c1subs);
        double[][] c2subs = {
                {1.0, 0.5},
                {2.0, 1.0}
        };
        subcriteriaRates.put(c2, c2subs);
        double[][] c3subs = {
                {1.0, 0.333},
                {3.0, 1.0}
        };
        subcriteriaRates.put(c3, c3subs);
        dm.setSubCriteriasRates(subcriteriaRates);

        // create alternatives rates
        HashMap<Criteria, double[][]> alternativesRates = new HashMap<>();

        double[][] c1s1alt = {
                {1.0, 0.14, 0.2},
                {7.0, 1.0, 7.0},
                {5.0, 0.1429, 1}
        };
        alternativesRates.put(c1s1, c1s1alt);

        double[][] c1s2alt = {
                {1.0, 0.1111, 0.1429},
                {9.0, 1.0, 5.0},
                {7.0, 0.2, 1.0}
        };
        alternativesRates.put(c1s2, c1s2alt);

        double[][] c2s1alt = {
                {1.0, 0.1429, 0.3333},
                {7.0, 1.0, 3.0},
                {3.0, 0.3333, 1.0}
        };
        alternativesRates.put(c2s1, c2s1alt);

        double[][] c2s2alt = {
                {1.0, 2.0, 3.0},
                {0.5, 1.0, 5.0},
                {0.3333, 0.2, 1.0}
        };
        alternativesRates.put(c2s2, c2s2alt);

        double[][] c3s1alt = {
                {1.0, 1.0, 9.0},
                {1.0, 1.0, 9.0},
                {0.1111, 0.1111, 1.0}
        };
        alternativesRates.put(c3s1, c3s1alt);

        double[][] c3s2alt = {
                {1.0, 0.2, 0.1429},
                {5.0, 1.0, 0.3333},
                {7.0, 3.0, 1.0}
        };
        alternativesRates.put(c3s2, c3s2alt);
        dm.setAlternativesRates(alternativesRates);

        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(dm);
        System.out.println("Вектор: " + ahp.getVector());
    }
}
