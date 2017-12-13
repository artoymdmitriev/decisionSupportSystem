package main.java.com.decisionSupportSystem.logic;

public class Criteria {
    private String name;
    private Criteria parent;

    public Criteria(String name) {
        this.name = name;
    }

    public Criteria(String name, Criteria parent) {
        this.name = name;
        this.parent = parent;
    }

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
                ", parent=" + parent +
                '}';
    }
}
