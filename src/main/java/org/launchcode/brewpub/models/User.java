package org.launchcode.brewpub.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User extends AbstractEntity{

    @ManyToMany(mappedBy = "pubFavoriteUser")
    private final List<Pub> favoritePubs = new ArrayList<>();

    @ManyToMany(mappedBy = "brewFavoriteUser")
    private final List<Brew> favoriteBrews = new ArrayList<>();

    @NotNull
    @NotBlank
    @Size(min =2, max = 40, message = "First name must be between 2 and 40 characters")
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min =2, max = 40, message = "Last name must be between 2 and 40 characters")
    private String lastName;

    @NotNull
    @Email(message = "Please enter a valid email")
    @Size(max = 120)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 5, max =40 , message = "Invalid username. Must be between 5 and 40 characters.")
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

    public String getUsername() {
        return username;
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

    public List<Pub> getFavoritePubs() {
        return favoritePubs;
    }

    public void addFavoritePub(Pub pub) {
        this.favoritePubs.add(pub);
    }

    public void removeFavoritePub(Pub pub) {
        this.favoritePubs.remove(pub);
    }

    public List<Brew> getFavoriteBrews() {
        return favoriteBrews;
    }

    public void addFavoriteBrew(Brew brew) {
        this.favoriteBrews.add(brew);
    }

    public void removeFavoriteBrew(Brew brew) {
        this.favoriteBrews.remove(brew);
    }
}
