package com.dohman.myexercise;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    @SerializedName("login")
    private String login;
    @SerializedName("id")
    private String id;
    @SerializedName("avatar_url")
    private String avatar_url;
    @SerializedName("public_repos")
    private int public_repos;

    public User(String login, String id, String avatar_url, int public_repos) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.public_repos = public_repos;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }
}