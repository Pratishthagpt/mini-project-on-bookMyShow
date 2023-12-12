package com.miniproject.bookMyShow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity(name = "shows")
@Getter
@Setter
public class Show extends BaseModel{
    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Auditorium auditorium;

    private Date startTime;

    private Date endTIme;

    @OneToMany(mappedBy = "show")
    private List<ShowSeat> showSeats;
}
