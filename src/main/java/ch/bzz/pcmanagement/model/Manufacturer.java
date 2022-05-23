package ch.bzz.pcmanagement.model;

/**
 * the manufacturer of one or more PCs
 */
public class Manufacturer {
    private String name;
    private String origin;
    private String tel;
    private String email;
    private int id;

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
