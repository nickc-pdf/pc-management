package ch.bzz.pcmanagement.model;

import ch.bzz.pcmanagement.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.util.List;

/**
 * a PC in our pc management
 */
public class PC {

    private int id;

    @FormParam("name")
    @NotEmpty
    @Size(min = 5, max = 30)
    private String name;

    //@FormParam("components") List<Component> components,
    @JsonIgnore
    private List<Component> components;

    @JsonIgnore
    private Manufacturer manufacturer;

    @FormParam("price")
    @NotNull
    @DecimalMin("0.05")
    @DecimalMax("9999.95")
    private BigDecimal price;


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

    public int getManufacturerID() {
        if (getManufacturer()== null) return 0;
        return getManufacturer().getId();
    }

    public void setManufacturerID(int manufacturerID) {
        setManufacturer(new Manufacturer());
        Manufacturer manufacturer = DataHandler.readManufacturerID(manufacturerID);
        getManufacturer().setId(manufacturerID);
        getManufacturer().setName(manufacturer.getName());
        getManufacturer().setEmail(manufacturer.getEmail());
        getManufacturer().setTel(manufacturer.getTel());
        getManufacturer().setOrigin(manufacturer.getOrigin());
        getManufacturer().setManufacturer(manufacturer.getManufacturer());
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
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * sets price
     * @param price value to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
