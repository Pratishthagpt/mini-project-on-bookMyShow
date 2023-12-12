package com.miniproject.bookMyShow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Theatre extends BaseModel{
    private String name;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "theatre")
    private List<Auditorium> auditoriums;

}
