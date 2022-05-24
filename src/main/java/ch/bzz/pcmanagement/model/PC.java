package ch.bzz.pcmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * a PC in our pc management
 */
public class PC {
    private String name;
    @JsonIgnore
    private List<Component> components;
    @JsonIgnore
    private Manufacturer manufacturer;
    private int id;
    private double price;

    /**
     * gets name
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets components
     * @return value of components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * sets components
     * @param components value to set
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * gets manufacturer
     * @return value of manufacturer
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * sets manufacturer
     * @param manufacturer value to set
     */
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * gets id
     * @return value of id
     */
    public int getId() {
        return id;
    }

    /**
     * sets id
     * @param id value to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gest price
     * @return value of price
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price value to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
