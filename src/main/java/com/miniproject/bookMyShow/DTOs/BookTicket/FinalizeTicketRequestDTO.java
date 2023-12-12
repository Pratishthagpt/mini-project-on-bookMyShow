package com.miniproject.bookMyShow.DTOs.BookTicket;

import com.miniproject.bookMyShow.Models.Booking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalizeTicketRequestDTO {
    private Booking booking;

}
