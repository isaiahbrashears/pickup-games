package com.codeup.blog.blog.models;


import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Game(String title, String location, User user, List comments) {
        this.title = title;
        this.location = location;
        this.user = user;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}

