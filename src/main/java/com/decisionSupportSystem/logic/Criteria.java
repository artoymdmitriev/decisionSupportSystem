package main.java.com.decisionSupportSystem.logic;

public class Criteria {
    private String name;

    public Criteria(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
