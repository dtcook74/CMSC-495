package app;

/*
 * Filename: Ingredient.java
 * Authors: Team Delta - Dustin Cook, Zachary Pesons, David Solan, Nathan Wray
 * Purpose: Ingredient objects represent an ingredient in a MenuItem with quantity and measurement
 * Date: April 19, 2021
 */
public class Ingredient implements Comparable<Ingredient> {

    private String name;
    private String quantity;
    private String measurementUnit;

    public Ingredient() {
    }

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = Integer.toString(quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @Override
    public String toString() {
        return String.format("%s: %s %s", name, quantity, measurementUnit);
    }

    @Override
    public int compareTo(Ingredient ingredient) {
        if (name.equalsIgnoreCase(ingredient.name)) {
            return 0;
        } else {
            return -1;
        }
    }
}
