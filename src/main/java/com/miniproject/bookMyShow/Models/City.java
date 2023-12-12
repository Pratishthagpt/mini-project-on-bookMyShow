package com.miniproject.bookMyShow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class City extends BaseModel{
    private String name;
    private String state;

    @OneToMany(mappedBy = "city")
    private List<Theatre> theatres;

}
