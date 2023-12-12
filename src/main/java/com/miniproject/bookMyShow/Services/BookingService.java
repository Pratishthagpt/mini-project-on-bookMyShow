package com.miniproject.bookMyShow.Services;

import com.miniproject.bookMyShow.Exceptions.Booking.BookingNotFoundException;
import com.miniproject.bookMyShow.Exceptions.CancelBooking.InvalidShowException;
import com.miniproject.bookMyShow.Exceptions.Booking.SeatNotAvailableException;
import com.miniproject.bookMyShow.Exceptions.CancelBooking.ShowStartedException;
import com.miniproject.bookMyShow.Exceptions.UserValidation.InvalidUserDetailsException;
import com.miniproject.bookMyShow.Models.*;
import com.miniproject.bookMyShow.Repositories.BookingRepository;
import com.miniproject.bookMyShow.Repositories.ShowRepository;
import com.miniproject.bookMyShow.Repositories.ShowSeatRepository;
import com.miniproject.bookMyShow.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {
    private UserRepository userRepository;
    private ShowRepository showRepository;
    private ShowSeatRepository showSeatRepository;
    private PricingService pricingService;
    private BookingRepository bookingRepository;
    private PaymentService paymentService;

    @Autowired
    public BookingService(UserRepository userRepository,
                          ShowRepository showRepository,
                          ShowSeatRepository showSeatRepository,
                          PricingService pricingService,
                          BookingRepository bookingRepository,
                          PaymentService paymentService) {
        this.userRepository = userRepository;
        this.showRepository = showRepository;
        this.showSeatRepository = showSeatRepository;
        this.pricingService = pricingService;
        this.bookingRepository = bookingRepository;
        this.paymentService = paymentService;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking bookMovieTickets (Long userId, Long showId, List<Long> showSeatIds) {
        /**
         * This bookMovieTicket() is only there to block the tickets before the payment, and it also takes
         * care of the concurrency (only one user should be able to book ticket at a time)
         *  ---------------Taking a lock here to make things simple for now------------------------
         *  1. Create new ticket.
         *  2. Get user from userId.
         *  3. Get show from showId.
         *  ----------------------------------Take a lock-------------------------------------------
         *  4. Get List of show seats from showSeatId.
         *  5. Check if the showSeats are available.
         *  6. Change the status of showSeats to locked.
         *  ----------------------------------Release a lock------------------------------------------
         *  7. Save updated show seats in DB and end the lock.
         *  8. Get amount from showSeatType.
         *  9. Save the booking into repository.
         *  10. Return the booking.
         * */

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new InvalidUserDetailsException("Invalid user Id!!");
        }
        User user = userOptional.get();

        Optional<Show> showOptional = showRepository.findById(showId);
        if (showSeatIds.isEmpty()) {
            throw new InvalidShowException("Invalid show Id!!");
        }
        Show bookedShow = showOptional.get();

        /* While getting the showSeats by their Ids, we will also check following conditions -
            1. ShowSeat status should be AVAILABLE, OR
            2. If the show seat status is LOcKED && The time duration between the blocked time and
               current time > 15
            then, change the status to BLOCKED.
        * */

        List<ShowSeat> showSeats = showSeatRepository.findAllById(showSeatIds);
        List<ShowSeat> savedShowSeats = new ArrayList<>();

        for (ShowSeat showSeat : showSeats) {
            if (!((showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)) ||
                    ((showSeat.getShowSeatStatus().equals(ShowSeatStatus.BLOCKED)) &&
                            ((int) TimeUnit.MILLISECONDS.toMinutes(showSeat.getBlockedAt().getTime() -
                                    new Date().getTime()) > 15)))) {
                throw new SeatNotAvailableException("Sorry!! Seats are not available!!");
            }
            else {
                showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
                showSeat.setBlockedAt(new Date());
                savedShowSeats.add(showSeatRepository.save(showSeat));
            }
        }

        Booking booking = new Booking();
        booking.setBookedBy(user);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setShow(bookedShow);
        booking.setShowSeats(savedShowSeats);
        booking.setAmount(pricingService.getPrice(savedShowSeats, bookedShow));
        booking.setBookedAt(new Date());

        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
    }

    public Booking finalizeBooking (Booking booking) {
//        Proceed for payment
        Booking finalBooking = paymentService.initializePayment(booking, booking.getBookedBy(),
                                                                booking.getAmount());
        finalBooking.setBookingStatus(BookingStatus.CONFIRMED);

        return bookingRepository.save(finalBooking);
    }

    public void cancelTicket (Long bookingId, Long userId) {

//        Check if user account exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new InvalidUserDetailsException("Invalid user Id!!");
        }
        User user = userOptional.get();

//        Check if booking is already made with the given booking id
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isEmpty()) {
            throw new BookingNotFoundException("Invalid Booking!! Booking not found with the given Booking Id.");
        }
        Booking booking = bookingOptional.get();

//        Check that booking cannot be cancelled if the show has already started
        Show show = booking.getShow();
        if (show.getStartTime().compareTo(new Date()) < 0) {
            throw new ShowStartedException("Show has already started. Booking cannot be cancelled!!");
        }

//        Updating the show seats status to AVAILABLE
        List<ShowSeat> bookedShowSeats = booking.getShowSeats();
        for(ShowSeat showSeat : bookedShowSeats) {
            showSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
        }

//        Updating the payment status to REFUNDED
        System.out.println("Refund has been initiated.");
        List<Payment> payments = booking.getPayments();
        for (Payment payment : payments) {
            payment.setPaymentStatus(PaymentStatus.REFUNDED);
        }
//        Remove the booking from booking repository or database

        bookingRepository.delete(booking);

    }


}
