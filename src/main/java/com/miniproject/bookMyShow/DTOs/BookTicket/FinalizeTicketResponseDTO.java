package com.miniproject.bookMyShow.DTOs.BookTicket;

import com.miniproject.bookMyShow.DTOs.ResponseStatus;
import com.miniproject.bookMyShow.Models.Booking;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinalizeTicketResponseDTO {

    private Booking booking;
    private ResponseStatus responseStatus;
    private String failureReason;
}
