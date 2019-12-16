package com.codeup.blog.blog.models;


import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(250)")
    private String title;

    @Column(nullable = false, columnDefinition = "VARCHAR(250)")
    private String location;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    public Game(String title, String location, User user) {
        this.title = title;
        this.location = location;
        this.user = user;
    }

    public Game() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

