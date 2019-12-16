package com.codeup.blog.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(80)", unique = true)
    private String username;

    @Column(nullable = false, columnDefinition = "VARCHAR(200)", unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(250)")
    private String password;

    @OneToOne
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Game> games;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User() {}

    public User(String username, String email, String password, List<Game> games, List<Comment> comments) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.games = games;
        this.comments = comments;
    }

    public User(long id, String username, String email, String password, Profile profile, List<Game> games, List<Comment> comments) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.id = id;
        this.profile = profile;
        this.games = games;
        this.comments = comments;
    }

    public User(User user) {
        this.id = user.id;
        this.username = user.username;
        this.password = user.password;
        this.email = user.email;
        this.profile = user.profile;
        this.games = user.games;
        this.comments = user.comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

