package ch.bzz.pcmanagement.model;

import ch.bzz.pcmanagement.util.LocalDateDeserializer;
import ch.bzz.pcmanagement.util.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.util.Date;

/**
 * the component of one or PCs
 */
public class Component {

    @FormParam("name")
    @NotEmpty
    @Size(min = 5,max = 30)
    private String name;

    @FormParam("description")
    @Size(max = 120)
    private String description;

    @FormParam("generation")
    @Size(max = 15)
    private String generation;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
     * sets releaseDate
     * @param releaseDate value to set
     */
    @JsonIgnore
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = LocalDate.parse(releaseDate);
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
