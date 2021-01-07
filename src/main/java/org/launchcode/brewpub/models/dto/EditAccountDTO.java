package org.launchcode.brewpub.models.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EditAccountDTO {
    @NotNull
    @NotBlank
    @Size(min = 7, max = 30, message = "Invalid password. Must be between 7 and 30 characters.")
    private String password;

    private String verifyPassword;

    @NotBlank
    @Email(message = "Please enter a valid email")
    @Size(max = 120)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 5, max =40 , message = "Invalid username. Must be between 5 and 40 characters.")
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }
}

