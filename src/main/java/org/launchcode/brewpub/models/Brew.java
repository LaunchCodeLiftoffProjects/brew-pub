package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Brew extends AbstractEntity{

    @ManyToOne
    private Pub pub;


    @Size(min = 4, max = 80, message="Brew name must be between 4 and 80 characters")
    @NotBlank
    private String name;

    @Min(value = 0, message = "IBU cannot be less than zero")
    @Max(value=3000, message = "IBU cannot be greater than 3,000")
    private float ibu;

    @Min(value = 0, message = "ABV cannot be less than zero")
    @Max(value = 100, message = "ABV cannot be greater than 100")
    @NotBlank
    private float abv;

    @Size(max = 80, message="Brewer name cannot be more than 80 characters")
    private String brewer;

    @Size(max = 200, message="Description cannot be more than 200 characters")
    private String description;

    @Size(max = 80, message = "Style cannot be more than 80 characters")
    private String style;


    public Brew() {
    }

    public Brew(String name, float ibu, float abv, String brewer, String description, String style) {
        this.name = name;
        this.ibu = ibu;
        this.abv = abv;
        this.brewer = brewer;
        this.description = description;
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getIbu() {
        return ibu;
    }

    public void setIbu(float ibu) {
        this.ibu = ibu;
    }

    public float getAbv() {
        return abv;
    }

    public void setAbv(float abv) {
        this.abv = abv;
    }

    public String getBrewer() {
        return brewer;
    }

    public void setBrewer(String brewer) {
        this.brewer = brewer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
