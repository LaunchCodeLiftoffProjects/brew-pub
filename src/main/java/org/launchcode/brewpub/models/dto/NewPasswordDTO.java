package org.launchcode.brewpub.models.dto;

import com.sun.istack.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewPasswordDTO {

    @NotNull
    @NotBlank
    @Size(min = 7, max = 30, message = "Invalid password. Must be between 7 and 30 characters.")
    private String password;

    private String verifyPassword;

    @NotBlank
    @Email(message = "Please enter a valid email")
    @Size(max = 120)
    private String email;

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
}
