package main.java.com.decisionSupportSystem.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class DataModel extends Observable {
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

    public ArrayList<Criteria> getCriterias() {
        return criterias;
    }

    public HashMap<Criteria, ArrayList<Criteria>> getSubCriterias() {
        return subCriterias;
    }

    public void addSubcriteria(Criteria parentCriteria, ArrayList<Criteria> subcriterias) {
        subCriterias.put(parentCriteria, subcriterias);
        setChanged();
        notifyObservers();
    }

    public void addSubcriteria(Criteria parentCriteria, Criteria subCriteria) {
        subCriterias.get(parentCriteria).add(subCriteria);
        setChanged();
        notifyObservers();
    }

    public void addCriteria(Criteria criteria) {
        criterias.add(criteria);
        setChanged();
        notifyObservers();
    }
}
