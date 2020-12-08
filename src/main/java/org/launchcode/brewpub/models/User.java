package org.launchcode.brewpub.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User extends AbstractEninty{
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

    @NotBlank
    @Size(min = 5, max = 40, message = "Last name must be between 2 and 40 characters")
    private String username;

    @NotNull
    private String pwhash;

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {};

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.pwhash = encoder.encode(password);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwhash() {
        return pwhash;
    }

    public void setPwhash(String pwhash) {
        this.pwhash = pwhash;
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwhash);
    }

}
