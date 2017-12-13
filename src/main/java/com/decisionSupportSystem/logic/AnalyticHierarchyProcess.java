package main.java.com.decisionSupportSystem.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnalyticHierarchyProcess {
    private ArrayList<Alternative> alternatives;
    private ArrayList<Criteria> criterias;
    private double criteriasComparison[][];
    private HashMap<Criteria, double[][]> subcriteriasComparison;

    public HashMap<Alternative, Double> getVector() {
        // Criterias weights
        double[] criteriasAvgGeometric = avgGeometric(criteriasComparison);
        double sumCriteriasAvgGeometric = sumColumn(criteriasAvgGeometric);
        double[] criteriasWeights = weightsVector(criteriasAvgGeometric, sumCriteriasAvgGeometric);

        // Subcriterias weights
        HashMap<Criteria, double[]> subcriteriasWeights= new HashMap<>();
        for(Map.Entry<Criteria, double[][]> entry : subcriteriasComparison.entrySet()) {
            double[] subcriteriasAvgGeometric = avgGeometric(entry.getValue());
            double sumSubcriteriasAvgGeometric = sumColumn(subcriteriasAvgGeometric);
            subcriteriasWeights.put(entry.getKey(), weightsVector(subcriteriasAvgGeometric, sumSubcriteriasAvgGeometric));
        }

        return new HashMap<>();
    }

    public ArrayList<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(ArrayList<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public ArrayList<Criteria> getCriterias() {
        return criterias;
    }

    public void setCriterias(ArrayList<Criteria> criterias) {
        this.criterias = criterias;
    }

    public double[][] getCriteriasComparison() {
        return criteriasComparison;
    }

    public void setCriteriasComparison(double[][] criteriasComparison) {
        this.criteriasComparison = criteriasComparison;
    }

    public HashMap<Criteria, double[][]> getSubcriteriasComparison() {
        return subcriteriasComparison;
    }

    public void setSubcriteriasComparison(HashMap<Criteria, double[][]> subcriteriasComparison) {
        this.subcriteriasComparison = subcriteriasComparison;
    }

    /**
     * Finds geometric mean for every row of the matrix.
     * */
    private double[] avgGeometric(double[][] matrix) {
        return new double[0];
    }

    /**
     * Finds the sum of all rows in column.
     * */
    private double sumColumn(double[] column) {
        return 0;
    }

    /**
     * Finds weights for each criteria/subcriteria
     * (avgGeometric[i]/sumColumn)
     * */
    private double[] weightsVector(double[] avgGeometric, double sumAvgGeometrics) {
        return new double[0];
    }

    /**
     * Multiplies two matrices
     */
    private double[] multiplyMatrices(double[][] matrix1, double[][] matrix2) {
        return new double[0];
    }
}
