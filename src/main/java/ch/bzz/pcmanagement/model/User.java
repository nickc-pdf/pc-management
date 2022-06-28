package ch.bzz.pcmanagement.model;

public class User {
    private String userID;
    private String username;
    private String password;
    private String role;

    public User(){
        setRole("guest");
    }

    /**
     * gets userUUID
     *
     * @return value of userUUID
     */

    public String getUserID() {
        return userID;
    }

    /**
     * sets userUUID
     *
     * @param userID the value to set
     */

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * gets username
     *
     * @return value of username
     */

    public String getUsername() {
        return username;
    }

    /**
     * sets username
     *
     * @param username the value to set
     */

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets password
     *
     * @return value of password
     */

    public String getPassword() {
        return password;
    }

    /**
     * sets password
     *
     * @param password the value to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets role
     *
     * @return value of role
     */

    public String getRole() {
        return role;
    }

    /**
     * sets role
     *
     * @param role the value to set
     */

    public void setRole(String role) {
        this.role = role;
    }
}
