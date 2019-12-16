package com.codeup.blog.blog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String name;

    @ManyToOne
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "profile_sports",
            joinColumns = {@JoinColumn(name="sport_id")},
            inverseJoinColumns = {@JoinColumn(name="profile_id")}
    )
    private List<Profile> profiles;

    public Sport() { }

    public Sport(String name, Game game, List<Profile> profiles) {
        this.name = name;
        this.game = game;
        this.profiles = profiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
