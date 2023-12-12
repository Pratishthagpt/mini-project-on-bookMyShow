package com.miniproject.bookMyShow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Auditorium extends BaseModel{
    private String name;

    @ManyToOne
    private Theatre theatre;

    @OneToMany(mappedBy = "auditorium")
    private List<PhysicalSeat> seats;

    @Enumerated(value = EnumType.ORDINAL)
    @ElementCollection
    private List<Feature> featuresList;

}
