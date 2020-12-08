package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class Pub extends AbstractEntity{
    @NotNull
    @NotBlank
    @Size(min =4, max = 125, message= "A Pub name must be entered.")
    private String name;

    @NotNull
    @NotBlank
    @Size(min =4, max = 125, message = "An address must be entered.")
    private String address;

    @NotNull
    @NotBlank
    @Size(min =1, max = 125, message = "A city must be entered.")
    private String city;

    @NotNull
    @NotBlank (message = "A state must be selected.")
    private String state;

    @Size(max = 5)
    private String areaCode;

    //@OneToMany
    //@JoinColumn
    //private final List<Brew> brews = new ArrayList<>();

    public Pub(String name, String address, String city, String state, String areaCode){
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.areaCode = areaCode;
    }

    public Pub(){ //no-arg constructor for Hibernate
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
