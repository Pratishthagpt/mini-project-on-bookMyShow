package com.miniproject.bookMyShow.Controllers;

import com.miniproject.bookMyShow.DTOs.*;
import com.miniproject.bookMyShow.DTOs.BookTicket.BookTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.BookTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.FinalizeTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.FinalizeTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.CancelTicket.CancelTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.CancelTicket.CancelTicketResponseDTO;
import com.miniproject.bookMyShow.Exceptions.Booking.BookingNotFoundException;
import com.miniproject.bookMyShow.Exceptions.CancelBooking.InvalidShowException;
import com.miniproject.bookMyShow.Exceptions.Booking.SeatNotAvailableException;
import com.miniproject.bookMyShow.Exceptions.CancelBooking.ShowStartedException;
import com.miniproject.bookMyShow.Exceptions.Payment.PaymentFailureException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidUserDetailsException;
import com.miniproject.bookMyShow.Models.Booking;
import com.miniproject.bookMyShow.Services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class BookingController {
    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public BookTicketResponseDTO bookTicket(BookTicketRequestDTO bookingRequest) {
        BookTicketResponseDTO ticketResponse = new BookTicketResponseDTO();

        Booking booking;
        try {
            booking = bookingService.bookMovieTickets(bookingRequest.getUserId(),
                    bookingRequest.getShowId(), bookingRequest.getShowSeatsId());

            ticketResponse.setBooking(booking);
            ticketResponse.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (SeatNotAvailableException | InvalidShowException | InvalidUserDetailsException ex) {
            ticketResponse.setResponseStatus(ResponseStatus.FAILURE);
            ticketResponse.setFailureReason(ex.getMessage());
        }

        return ticketResponse;
    }

    public FinalizeTicketResponseDTO finalizeTicket(FinalizeTicketRequestDTO bookingRequest) {
        FinalizeTicketResponseDTO responseDTO = new FinalizeTicketResponseDTO();

        Booking finalBooking;
        try {
            finalBooking = bookingService.finalizeBooking(bookingRequest.getBooking());

            responseDTO.setBooking(finalBooking);
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (PaymentFailureException e ) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
            responseDTO.setFailureReason(e.getMessage());
        }

        return responseDTO;
    }

    public void cancelTicket (CancelTicketRequestDTO cancelRequest) {
        CancelTicketResponseDTO cancelTicketResponseDTO = new CancelTicketResponseDTO();

        try {
            bookingService.cancelTicket(cancelRequest.getBookingId(), cancelRequest.getUserId());
            cancelTicketResponseDTO.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (BookingNotFoundException | ShowStartedException e) {
            cancelTicketResponseDTO.setResponseStatus(ResponseStatus.FAILURE);
            cancelTicketResponseDTO.setFailureReason(e.getMessage());
        }

    }
}
