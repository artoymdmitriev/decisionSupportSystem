package test.java;

import main.java.com.decisionSupportSystem.logic.Alternative;
import main.java.com.decisionSupportSystem.logic.AnalyticHierarchyProcess;
import main.java.com.decisionSupportSystem.logic.Criteria;
import main.java.com.decisionSupportSystem.logic.DataModel;

import java.util.ArrayList;
import java.util.HashMap;

public class TestAnalyticHierarchyProcess {
    public static void main(String[] args) {
        testAvgGeometric();
        testSumColumn();
        testWeightsVector();
        testMultiplyMatrices();
    }

    private static void testAHP() {
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

    private static void testAvgGeometric() {
        System.out.println("--- AVG GEOMETRIC TEST START ---");
        double matrix[][] = new double[2][2];
        matrix[0][0] = 1;
        matrix[0][1] = 0.2;
        matrix[1][0] = 5;
        matrix[1][1] = 1;

        System.out.println("Matrix: ");
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(null);
        double[] avgGeometric = ahp.avgGeometric(matrix);

        System.out.println("Avg Geometric: ");
        for(int i = 0; i < avgGeometric.length; i++) {
            System.out.println(avgGeometric[i]);
        }
        System.out.println("--- AVG GEOMETRIC TEST END ---");
    }

    private static void testSumColumn() {
        System.out.println("--- SUM COLUMN TEST START ---");
        double[] column = {1.0, 2.0, 3.0};

        System.out.println("Column is: ");
        for(int i = 0; i < column.length; i++) {
            System.out.println(column[i]);
        }

        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(null);
        System.out.println("Sum is: " + ahp.sumColumn(column));
        System.out.println("--- SUM COLUMN TEST END ---");
    }

    private static void testWeightsVector() {
        System.out.println("--- WEIGHTS VECTOR TEST START ---");
        double[] elements = {1.0, 2.0, 3.0};
        System.out.println("Columns is: ");
        for(int i = 0; i < elements.length; i++) {
            System.out.println(elements[i]);
        }

        double sumOfElements = 6.0;

        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(null);
        double[] resultVector = ahp.weightsVector(elements, sumOfElements);

        System.out.println("Weights vector: ");
        for(int i = 0; i < resultVector.length; i++) {
            System.out.println(resultVector[i]);
        }

        System.out.println("--- WEIGHTS VECTOR TEST END ---");
    }

    private static void testMultiplyMatrices () {
        System.out.println("--- TEST MULTIPLY MATRICES START ---");
        double matrix[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        double array[] = {1, 2, 3};

        AnalyticHierarchyProcess ahp = new AnalyticHierarchyProcess(null);
        double result[] = ahp.multiplyMatrices(matrix, array);

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
        System.out.println("--- TEST MULTIPLY MATRICES END ---");
    }
}
