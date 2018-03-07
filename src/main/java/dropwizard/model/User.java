package dropwizard.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import dropwizard.View;

import java.security.Principal;

public class User implements Principal {
    @NotEmpty
    @Length(min = 1, max = 8)
    @JsonView(View.Private.class)
    private int id;

    @NotEmpty
    @Length(min = 3, max = 50)
    @JsonView(View.Public.class)
    private String username;

    @NotEmpty
    @Length(min = 8, max = 255)
    @JsonView(View.Protected.class)
    private String password;

    @NotEmpty
    @Length(min = 1, max = 255)
    @JsonView(View.Public.class)
    private String firstname;

    @NotEmpty
    @Length(min = 1, max = 24)
    @JsonView(View.Public.class)
    private String preposition;

    @NotEmpty
    @Length(min = 1, max = 255)
    @JsonView(View.Public.class)
    private String lastname;

    @NotEmpty
    @Length(min = 6, max = 512)
    @JsonView(View.Public.class)
    private String email;

    @NotEmpty
    @Length(min = 5, max = 5)
    @JsonView(View.Public.class)
    private String role;

    @NotEmpty
    @Length(min = 10, max = 10)
    @JsonView(View.Public.class)
    private String createdAt;

    @NotEmpty
    @Length(min = 10, max = 10)
    @JsonView(View.Public.class)
    private String updatedAt;

    @NotEmpty
    @JsonView(View.Protected.class)
    private Boolean actief;

    public User(){

    }

    public User(String username, String password, String firstname, String preposition, String lastname, String email, String role, Boolean actief){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.preposition = preposition;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.actief = actief;
    }

    public User(int id, String username, String password, String firstname, String preposition, String lastname, String email, String role, String createdAt, String updatedAt, Boolean actief) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.preposition = preposition;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.actief = actief;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActief() {
        return actief;
    }

    public void setActief(Boolean actief) {
        this.actief = actief;
    }
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getPreposition() {
//        return preposition;
//    }
//
//    public void setPreposition(String preposition) {
//        this.preposition = preposition;
//    }
//
//    public String getLastname() {
//        return lastname;
//    }
//
//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public Boolean getActief() {
//        return actief;
//    }
//
//    public void setActief(Boolean actief) {
//        this.actief = actief;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return UpdatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        UpdatedAt = updatedAt;
//    }

    @Override
    public String getName() {
        return null;
    }

}
