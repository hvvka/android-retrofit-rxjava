package com.hania.model;

import java.io.Serializable;

public class User implements Serializable {

    private String login;

    private Integer contributions;

    private String name;

    private String company;

    private String location;

    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getContributions() {
        return contributions;
    }

    public void setContributions(Integer contributions) {
        this.contributions = contributions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contributor [login=" + login + ", contributions=" + contributions
                + ", name=" + name + ", company=" + company
                + ", location=" + location + ", email=" + email + "]";
    }
}
