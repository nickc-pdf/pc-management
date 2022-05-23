package ch.bzz.pcmanagement.model;

import java.time.LocalDate;

/**
 * the component of one or PCs
 */
public class Component {
    private String name;
    private String description;
    private String generation;
    private LocalDate releaseDate;
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
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets description
     * @return value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets description
     * @param description value to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets generation
     * @return value of generation
     */
    public String getGeneration() {
        return generation;
    }

    /**
     * sets generation
     * @param generation value to set
     */
    public void setGeneration(String generation) {
        this.generation = generation;
    }

    /**
     * gets releaseDate
     * @return value of releaseDate
     */
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    /**
     * sets releaseDate
     * @param releaseDate value to set
     */
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
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
