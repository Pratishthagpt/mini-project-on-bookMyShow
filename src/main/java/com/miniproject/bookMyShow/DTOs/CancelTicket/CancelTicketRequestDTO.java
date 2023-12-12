package com.miniproject.bookMyShow.DTOs.CancelTicket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelTicketRequestDTO {
    private Long bookingId;
    private Long userId;

}
