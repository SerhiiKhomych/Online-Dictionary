package com.study.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "App_user")
public class User {
    @Id
    @JsonIgnore
    private String login;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(name = "repetition_mode")
    private RepetitionMode repetitionMode;

    @JsonIgnore
    @ElementCollection(targetClass=Category.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_categories")
    @Column(name="category")
    Set<Category> categories;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public RepetitionMode getRepetitionMode() {
        return repetitionMode;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setRepetitionMode(RepetitionMode repetitionMode) {
        this.repetitionMode = repetitionMode;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
