package org.launchcode.brewpub.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Pub extends AbstractEntity{
    @NotNull
    @NotBlank
    @Size(min =4, max = 125)
    private String name;

    @NotNull
    @NotBlank
    @Size(min =4, max = 125)
    private String address;

    @NotNull
    @NotBlank
    @Size(min =1, max = 125)
    private String city;

    @NotNull
    @NotBlank
    private String state;

    @Size(max = 5)
    private String areaCode;

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
