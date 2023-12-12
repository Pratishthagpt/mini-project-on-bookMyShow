package com.miniproject.bookMyShow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel{
    private String name;
    private String IMDB_rating;
    private String description;
    private int durationInMinutes;

    @ManyToMany
    private List<Genre> genres;

    @ManyToMany
    private List<Language> language;

    @Enumerated(value = EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> features;

}
