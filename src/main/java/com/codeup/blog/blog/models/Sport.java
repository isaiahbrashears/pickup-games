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

    @OneToMany(mappedBy = "sport", cascade = CascadeType.ALL)
    private List<Game> games;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "sports_profiles",
            joinColumns = {@JoinColumn(name="sport_id")},
            inverseJoinColumns = {@JoinColumn(name="profile_id")}
    )
    private List<Profile> profiles;

    public Sport() { }

    public Sport(String name, List<Game> games, List<Profile> profiles) {
        this.name = name;
        this.games = games;
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

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

}

