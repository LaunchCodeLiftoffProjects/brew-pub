package org.launchcode.brewpub.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotNull
    @NotBlank
    @Size(min = 5, max =40 , message = "Invalid username. Must be between 5 and 40 characters.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 7, max = 30, message = "Invalid password. Must be between 7 and 30 characters.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
