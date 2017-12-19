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

        // rates of criterias F33:F35
        HashMap<Criteria, Double> criteriasRates = new HashMap<>();
        ArrayList<Criteria> cl = dataModel.getCriterias();
        double[] cr = getWeights(dataModel.getCriteriasRates());
        for(int i = 0; i < cl.size(); i++) {
            criteriasRates.put(cl.get(i), cr[i]);
        }

        System.out.println("--- Веса критериев: ");
        for(Map.Entry<Criteria, Double> entry : criteriasRates.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        // rates of subcriterias like E39:E40, Criteria is an actual critera
        HashMap<Criteria, double[]> subcriteriasRates = new HashMap<>();
        HashMap<Criteria, double[][]> sr = dataModel.getSubCriteriasRates();
        for(Map.Entry<Criteria, double[][]> entry : sr.entrySet()) {
            subcriteriasRates.put(entry.getKey(), getWeights(entry.getValue()));
        }

        System.out.println("--- Веса подкритериев для каждого критерия: ");
        for(Map.Entry<Criteria, double[]> entry : subcriteriasRates.entrySet()) {
            System.out.println("Критерий: " + entry.getKey());
            for(int i = 0; i < entry.getValue().length; i++) {
                System.out.println(dataModel.getSubCriterias().get(entry.getKey()).get(i) + " " + entry.getValue()[i]);

            }
        }

        // rates of alternatives like F44:F46, Criteria = subcritera
        HashMap<Criteria, double[]> alternativesRates = new HashMap<>();
        HashMap<Criteria, double[][]> ar = dataModel.getAlternativesRates();
        for(Map.Entry<Criteria, double[][]> entry : ar.entrySet()) {
            alternativesRates.put(entry.getKey(), getWeights(entry.getValue()));
        }

        System.out.println("--- Веса альтернатив для каждого подкритерия: ");
        for(Map.Entry<Criteria, double[]> entry : alternativesRates.entrySet()) {
            System.out.println("Подкритерий: " + entry.getKey());
            for(int i = 0; i < entry.getValue().length; i++) {
                System.out.println(dataModel.getAlternatives().get(i) + " " + entry.getValue()[i]);
            }
        }

        System.out.println("------------------------- ляляляляляляляля -----------------");
        // alternatives rates * subcriterias weights A71:F84
        HashMap<Criteria, double[]> subcriteriasAlternativesVector = new HashMap<>();
        System.out.println("--- Свертываем альтернативы и подкритерии: ");
        for(Criteria c : dataModel.getCriterias()) {
            int columnsAmount = 0;
            for(Map.Entry<Criteria, double[]> entry : alternativesRates.entrySet()) {
                if(entry.getKey().getParent().equals(c)) {
                    columnsAmount++;
                }
            }

            int rowsAmount = 0;
            for(Map.Entry<Criteria, double[]> entry : alternativesRates.entrySet()) {
                if(entry.getKey().getParent().equals(c)) {
                    rowsAmount = entry.getValue().length;
                }
            }
            // A72:B74
            System.out.println("Критерий: " + c + " Таблица на " + columnsAmount + " колонок и " + rowsAmount + " строк.");
            double alternativesVector[][] = new double[rowsAmount][columnsAmount];

            int x = 0;

            ArrayList<Criteria> orderOfSubcriterias = dataModel.getSubCriterias().get(c);
            for(Criteria crit : orderOfSubcriterias) {
                for(Map.Entry<Criteria, double[]> entry : alternativesRates.entrySet()) {
                    if(entry.getKey().equals(crit)) {
                        for(int i = 0; i < rowsAmount; i++) {
                            alternativesVector[i][x] = entry.getValue()[i];
                        }
                        x++;
                    }
                }
            }

            double matrixSubcriteria[] = subcriteriasRates.get(c);



            System.out.println("Альтернативы: ");
            for(int i = 0; i < alternativesVector.length; i++) {
                for(int j = 0; j < alternativesVector[i].length; j++) {
                    System.out.print(alternativesVector[i][j] + "      ");
                }
                System.out.println();
            }

            System.out.println("Подкритерии");
            for(int i = 0; i < matrixSubcriteria.length; i++) {
                System.out.println(matrixSubcriteria[i]);
            }

            double[] resultVector = multiplyMatrices(alternativesVector, matrixSubcriteria);

            for(int i = 0; i < resultVector.length; i++) {
                System.out.println(resultVector[i]);
            }
            subcriteriasAlternativesVector.put(c, resultVector);
        }

        for(Map.Entry<Criteria, double[]> entry : subcriteriasAlternativesVector.entrySet()) {
            System.out.println(entry.getKey());
            for(int i = 0; i < entry.getValue().length; i++) {
                System.out.println(entry.getValue()[i] + " ");
            }
        }

        int noc = subcriteriasAlternativesVector.size();
        int nor = 0;
        for(Map.Entry<Criteria, double[]> entry : subcriteriasAlternativesVector.entrySet()) {
            nor = entry.getValue().length;
        }

        double subcriteriasMatrix[][] = new double[nor][noc];
        int col = 0;
        for(int i = dataModel.getCriterias().size() - 1; i >= 0; i--) {
            for(Map.Entry<Criteria, double[]> entry : subcriteriasAlternativesVector.entrySet()) {
                if(entry.getKey().equals(dataModel.getCriterias().get(i))) {
                    for(int x = 0; x < entry.getValue().length; x++) {
                        subcriteriasMatrix[x][col] = entry.getValue()[x];
                    }
                    col++;
                }
            }
        }

        double[] criteriasWeights = new double[dataModel.getCriterias().size()];
        for(int i = 0; i < dataModel.getCriterias().size(); i++) {
            for(Map.Entry<Criteria, Double> crit : criteriasRates.entrySet()) {
                if(crit.getKey().equals(dataModel.getCriterias().get(i))) {
                    criteriasWeights[i] = crit.getValue();
                }
            }
        }

        double finalVector[] = multiplyMatrices(subcriteriasMatrix, criteriasWeights);
        for(int i = 0; i < finalVector.length; i++) {
            System.out.println(dataModel.getAlternatives().get(i) + " " + finalVector[i]);
        }

        System.out.println("---- Final matrix ----");
        for(int i = 0; i < subcriteriasMatrix.length; i++) {
            for(int j = 0; j < subcriteriasMatrix[i].length; j++) {
                System.out.print(subcriteriasMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("---- Criterias weights vector ----");
        for(int i = 0; i < criteriasWeights.length; i++) {
            System.out.print(criteriasWeights[i] + " ");
        }

        HashMap<Alternative, Double> result = new HashMap<>();
        result.put(new Alternative("Альтернатива1"), 0.33);
        result.put(new Alternative("Альтернатива2"), 0.33);
        result.put(new Alternative("Альтернатива3"), 0.33);
        return result;
    }

    private double[] getWeights(double[][] matrix) {
        double[] avgGeom = avgGeometric(matrix);
        double sumCol = sumColumn(avgGeom);
        return weightsVector(avgGeom, sumCol);
    }

    /**
     * Finds geometric mean for every row of the matrix.
     * */
    public double[] avgGeometric(double[][] matrix) {
        double[] multline = new double[matrix.length];
        double[] result = new double[matrix.length];
        double mult = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                mult = mult * matrix[i][j];
            }
            multline[i] = mult;
            mult = 1;

        }
        for (int i = 0; i < matrix.length; i++) {
            double avgGeom = Math.pow(multline[i], 1.0/matrix.length);
            result[i] = avgGeom;
        }
        return result;
    }

    /**
     * Finds the sum of all rows in column.
     * */
    public double sumColumn(double[] column) {
        double result = 0;
        for (int i = 0; i < column.length; i++) {
            result = result + column[i];
        }
        return result;
    }

    /**
     * Finds weights for each criteria/subcriteria
     * (avgGeometric[i]/sumColumn)
     * */
    public double[] weightsVector(double[] column, double sumOfColumn) {
        double finalResult[] = new double[column.length];
        for (int i = 0; i < column.length; i++) {
            finalResult[i] = column[i]/sumOfColumn;
        }
        return finalResult;
    }

    /**
     * Multiplies two matrices.
     */
    public double[] multiplyMatrices(double[][] matrix, double[] array) {
        double result[] = new double [matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                sum = sum + matrix[i][j] * array[j];
            }
            result[i] = sum;
        }
        return result;
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
