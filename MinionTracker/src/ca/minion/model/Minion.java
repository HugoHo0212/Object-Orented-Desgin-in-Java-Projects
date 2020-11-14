package ca.minion.model;
/**
 * Represent a minion's name and height, and number of evil deeds completed
 */
public class Minion {
    private String name;
    private double height;
    private int numOfEvilDeeds;

    public Minion(String name, double height){
        this.name = name;
        this.height = height;
        numOfEvilDeeds = 0;
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public int getNumOfEvilDeeds() {
        return numOfEvilDeeds;
    }

    public void incEvilDeed() {
        numOfEvilDeeds += 1;
    }

    public String getInfo() {
        return name + ", " + height + ", " + numOfEvilDeeds + " evil deed(s)";
    }

    @Override
    public String toString() {
        return getClass().getName() +
                "[" +
                "Name:" + name +
                ", Evil Deeds:" + numOfEvilDeeds +
                ", Height:" + height +
                ']';
    }
}
