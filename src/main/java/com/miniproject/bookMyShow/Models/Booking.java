package com.miniproject.bookMyShow.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Booking extends BaseModel{
    private int amount;

    @ManyToOne
    private Show show;

    @OneToMany
    private List<ShowSeat> showSeats;

    @OneToMany
    private List<Payment> payments;


    @Enumerated(value = EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    @ManyToOne
    private Auditorium auditorium;

    @ManyToOne
    private User bookedBy;

    private Date bookedAt;
}
