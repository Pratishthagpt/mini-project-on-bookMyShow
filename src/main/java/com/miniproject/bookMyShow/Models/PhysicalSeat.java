package com.miniproject.bookMyShow.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhysicalSeat extends BaseModel{
    private int rowVal;
    private int colVal;
    private int seatNum;

    @ManyToOne
    private Auditorium auditorium;

    @ManyToOne
    private SeatType seatType;
}
