package main.java.com.decisionSupportSystem.logic;

public class Subcriteria {
    private String name;
    private Criteria parent;

    public Subcriteria(String name, Criteria parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return name;
//    }


    @Override
    public String toString() {
        return "Subcriteria{" +
                "name='" + name + '\'' +
                ", parent=" + parent +
                '}';
    }
}
