package main.java.com.decisionSupportSystem.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class AnalyticHierarchyProcess {
    ArrayList<Alternative> alternatives;
    ArrayList<Criteria> criterias;
    ArrayList<Subcriteria> subcriterias;
    double[][] criteriasComparison;
    HashMap<Criteria, double[][]> subcriteriasComparison;

    public HashMap<Alternative, Double> getVector() {
        return new HashMap<>();
    }
}
