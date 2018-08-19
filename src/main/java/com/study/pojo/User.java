package com.study.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Embeddable
public class User {
    private String login;
    @JsonIgnore
    private String password;
    private Profile profile;
    private RepetitionMode repetitionMode;

    private Set<Category> categories;

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
