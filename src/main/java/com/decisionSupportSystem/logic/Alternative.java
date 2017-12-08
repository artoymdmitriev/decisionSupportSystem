package main.java.com.decisionSupportSystem.logic;

public class Alternative {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Alternative{" +
                "name='" + name + '\'' +
                '}';
    }
}