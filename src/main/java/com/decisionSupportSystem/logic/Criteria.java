package main.java.com.decisionSupportSystem.logic;

public class Criteria {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "name='" + name + '\'' +
                '}';
    }
}
