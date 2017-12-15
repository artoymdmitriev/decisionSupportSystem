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

    public double[][] getCriteriasRates() {
        return criteriasRates;
    }

    public void setCriteriasRates(double[][] criteriasRates) {
        this.criteriasRates = criteriasRates;
    }

    public HashMap<Criteria, ArrayList<Criteria>> getSubCriterias() {
        return subCriterias;
    }

    public void setSubCriterias(HashMap<Criteria, ArrayList<Criteria>> subCriterias) {
        this.subCriterias = subCriterias;
    }

    public HashMap<Criteria, double[][]> getSubCriteriasRates() {
        return subCriteriasRates;
    }

    public void setSubCriteriasRates(HashMap<Criteria, double[][]> subCriteriasRates) {
        this.subCriteriasRates = subCriteriasRates;
    }

    public HashMap<Criteria, double[][]> getAlternativesRates() {
        return alternativesRates;
    }

    public void setAlternativesRates(HashMap<Criteria, double[][]> alternativesRates) {
        this.alternativesRates = alternativesRates;
    }
}
