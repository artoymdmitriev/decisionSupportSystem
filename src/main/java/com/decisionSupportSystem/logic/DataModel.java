package main.java.com.decisionSupportSystem.logic;

import java.util.ArrayList;
import java.util.HashMap;

public class DataModel {
    // Alternatives list
    private ArrayList<Alternative> alternatives = new ArrayList<>();

    // Criterias and their rates
    private ArrayList<Criteria> criterias = new ArrayList<>();
    private double[][] criteriasRates;

    // Subcriterias and their rates
    private HashMap<Criteria, ArrayList<Criteria>> subCriterias = new HashMap<>();
    private HashMap<Criteria, double[][]> subCriteriasRates = new HashMap<>();

    // Alternatives comparison in each subcriteria
    private HashMap<Criteria, double[][]> alternativesRates = new HashMap<>();


}
