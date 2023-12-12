package com.miniproject.bookMyShow.DTOs.CancelTicket;

import com.miniproject.bookMyShow.DTOs.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelTicketResponseDTO {
    private ResponseStatus responseStatus;
    private String failureReason;
}
