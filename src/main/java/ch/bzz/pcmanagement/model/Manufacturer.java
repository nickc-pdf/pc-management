package ch.bzz.pcmanagement.model;

import javax.validation.constraints.*;
import javax.ws.rs.FormParam;

/**
 * the manufacturer of one or more PCs
 */
public class Manufacturer {
    @FormParam("name")
    @NotEmpty
    @Size(min = 5, max = 30)
    private String name;

    @FormParam("origin")
    @NotEmpty
    @Size(min = 5,max = 30)
    private String origin;

    @FormParam("tel")
    @NotEmpty
    @Pattern(regexp = "(\\b(0041|0)|\\B\\+41)(\\s?\\(0\\))?(\\s)?[1-9]{2}(\\s)?[0-9]{3}(\\s)?[0-9]{2}(\\s)?[0-9]{2}\\b")
    private String tel;

    @FormParam("email")
    @NotEmpty
    @Size(min = 6, max = 50)
    private String email;

    private String manufacturer;
    private int id;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * gets name
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     * @param name the value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets origin
     * @return value of origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * sets origin
     * @param origin value to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * gets tel
     * @return value of tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * sets tel
     * @param tel value to set
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * gets email
     * @return value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * sets email
     * @param email value to set
     */
    public void setEmail(String email) {
        this.email = email;
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
}
