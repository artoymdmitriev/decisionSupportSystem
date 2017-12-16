package main.java.com.decisionSupportSystem.logic;

public class Criteria {
    private String name;
    private Criteria parent;
    private double id;

    public Criteria(String name) {
        this.name = name;
        this.id = Math.random() * 1_000_000;
    }

    public Criteria(String name, Criteria parent) {
        this.name = name;
        this.parent = parent;
        this.id = Math.random() * 1_000_000;
    }

    public Criteria getParent() {
        return parent;
    }

    public void setParent(Criteria parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Criteria)) return false;

        Criteria criteria = (Criteria) o;

        if (Double.compare(criteria.id, id) != 0) return false;
        if (name != null ? !name.equals(criteria.name) : criteria.name != null) return false;
        return parent != null ? parent.equals(criteria.parent) : criteria.parent == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        temp = Double.doubleToLongBits(id);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
