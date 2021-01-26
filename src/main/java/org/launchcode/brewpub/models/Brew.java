package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Brew extends AbstractEntity{

    @ManyToOne
    private Pub pub;

    @ManyToMany
    private final List<User> brewFavoriteUser = new ArrayList<>();

    @Size(min = 3, max = 80, message="Name must be between 4 and 80 characters")
    private String name;

    @Min(value = 0, message = "IBU cannot be less than zero")
    @Max(value=3000, message = "IBU cannot be greater than 3,000")
    private Integer ibu;

    @Min(value = 0, message = "ABV cannot be less than zero")
    @Max(value = 100, message = "ABV cannot be greater than 100")
    private BigDecimal abv;

    @Size(max = 80, message="Brewer name cannot be more than 80 characters")
    private String brewer;

    @Size(max = 200, message="Description cannot be more than 200 characters")
    private String description;

    @Size(max = 80, message = "Style cannot be more than 80 characters")
    private String style;

    @Size(max = 150, message = "File name cannot be more than 150 characters")
    private String imagePath;


    public Brew() {
    }

    public Brew(String name, Integer ibu, BigDecimal abv, String brewer, String description, String style, String imagePath) {
        this.name = name;
        this.ibu = ibu;
        this.abv = abv;
        this.brewer = brewer;
        this.description = description;
        this.style = style;
        this.imagePath = imagePath;
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIbu() {
        return ibu;
    }

    public void setIbu(Integer ibu) {
        this.ibu = ibu;
    }

    public BigDecimal getAbv() {
        return abv;
    }

    public void setAbv(BigDecimal abv) {
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public List<User> getBrewFavoriteUser() {
        return brewFavoriteUser;
    }

    public void addBrewFavoriteUser(User user) {
        this.brewFavoriteUser.add(user);
    }

    public void removeBrewFavoriteUser(User user) {
        this.brewFavoriteUser.remove(user);
    }
}
