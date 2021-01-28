package org.launchcode.brewpub.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAccountDTO {

    @NotNull
    @NotBlank
    @Size(min = 5, max =40 , message = "Invalid username. Must be between 5 and 40 characters.")
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 7, max = 30, message = "Invalid password. Must be between 7 and 30 characters.")
    private String password;

    private String verifyPassword;

    @NotBlank
    @Size(min =2, max = 40, message = "First name must be between 2 and 40 characters")
    private String firstName;

    @NotBlank
    @Size(min =2, max = 40, message = "Last name must be between 2 and 40 characters")
    private String lastName;

    @NotBlank
    @Email(message = "Please enter a valid email")
    @Size(max = 120)
    private String email;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
