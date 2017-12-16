package main.java.com.decisionSupportSystem.logic;

import java.util.HashMap;

public class AnalyticHierarchyProcess {
    private DataModel dataModel;

    public AnalyticHierarchyProcess(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public HashMap<Alternative, Double> getVector() {
        return new HashMap<>();
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
}
