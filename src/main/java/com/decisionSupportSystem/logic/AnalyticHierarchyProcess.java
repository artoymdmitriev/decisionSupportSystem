package main.java.com.decisionSupportSystem.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyticHierarchyProcess {
    private DataModel dataModel;

    public AnalyticHierarchyProcess(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public HashMap<Alternative, Double> getVector() {
        printData();
        HashMap<Alternative, Double> result = new HashMap<>();
        result.put(new Alternative("Альтернатива1"), 0.33);
        result.put(new Alternative("Альтернатива2"), 0.33);
        result.put(new Alternative("Альтернатива3"), 0.33);
        return result;
    }

    /**
     * Finds geometric mean for every row of the matrix.
     * */
    public double[] avgGeometric(double[][] matrix) {
        return new double[0];
    }

    /**
     * Finds the sum of all rows in column.
     * */
    public double sumColumn(double[] column) {
        return 0;
    }

    /**
     * Finds weights for each criteria/subcriteria
     * (avgGeometric[i]/sumColumn)
     * */
    public double[] weightsVector(double[] avgGeometric, double sumAvgGeometrics) {
        return new double[0];
    }

    /**
     * Multiplies two matrices.
     */
    private double[] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        return new double[0];
    }

    private void printData() {
        System.out.println("Alternatives: ");
        for(int i = 0; i < dataModel.getAlternatives().size(); i++) {
            System.out.println(dataModel.getAlternatives().get(i));
        }

        System.out.println("Criterias: ");
        for(int i = 0; i < dataModel.getCriterias().size(); i++) {
            System.out.println(dataModel.getCriterias().get(i));
        }


        System.out.println("Subcriterias: ");
        for(Map.Entry<Criteria, ArrayList<Criteria>> entry : dataModel.getSubCriterias().entrySet()) {
            System.out.println("Parent: " + entry.getKey() + " --- " + entry.getValue());
        }

        System.out.println("Оценки критериев: ");
        for(int i = 0; i < dataModel.getCriteriasRates().length; i++) {
            for(int j = 0; j < dataModel.getCriteriasRates()[i].length; j++) {
                System.out.print(dataModel.getCriteriasRates()[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Оценки подкритериев: ");
        for(Map.Entry<Criteria, double[][]> entry : dataModel.getSubCriteriasRates().entrySet()) {
            System.out.println(entry.getKey());
            for(int i = 0; i < entry.getValue().length; i++) {
                for(int j = 0; j < entry.getValue()[i].length; j++) {
                    System.out.print(entry.getValue()[i][j] + " ");
                }
                System.out.println();
            }
        }

        System.out.println("Оценки альтернатив: ");
        for(Map.Entry<Criteria, double[][]> entry : dataModel.getAlternativesRates().entrySet()) {
            System.out.println(entry.getKey());
            for(int i = 0; i < entry.getValue().length; i++) {
                for(int j = 0; j < entry.getValue()[i].length; j++) {
                    System.out.print(entry.getValue()[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
