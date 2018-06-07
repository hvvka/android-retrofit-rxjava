package com.hania.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Repository implements Serializable {

    private String name;

    private String description;

    private User owner;

    private String language;

    @SerializedName("stargazers_count")
    private int stargazersCount;

    @SerializedName("forks_count")
    private int forksCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    @Override
    public String toString() {
        return "Repository [name=" + name + ", description=" + description
                + ", owner=" + owner.getName() + ", language=" + language
                + ", stargazers_count=" + stargazersCount + ", forks_count=" + forksCount + "]";
    }

    // todo date field
}
