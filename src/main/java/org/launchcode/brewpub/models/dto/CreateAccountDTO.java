package org.launchcode.brewpub.models.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateAccountDTO extends LoginDTO {
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
