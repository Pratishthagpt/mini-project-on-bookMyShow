package com.miniproject.bookMyShow.DTOs.BookTicket;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookTicketRequestDTO {
    private Long showId;
    private Long userId;
    private List<Long> showSeatsId;
}
