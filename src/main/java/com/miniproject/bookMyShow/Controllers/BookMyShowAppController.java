package com.miniproject.bookMyShow.Controllers;

import com.miniproject.bookMyShow.DTOs.BookTicket.BookTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.BookTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.FinalizeTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.FinalizeTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.CancelTicket.CancelTicketRequestDTO;
import com.miniproject.bookMyShow.DTOs.User.UserAuthResponseDTO;
import com.miniproject.bookMyShow.DTOs.User.UserLoginRequestDTO;
import com.miniproject.bookMyShow.DTOs.User.UserSignUpRequestDTO;
import com.miniproject.bookMyShow.Exceptions.Accounts.UserNotFoundException;
import com.miniproject.bookMyShow.Exceptions.Booking.SeatLimitExceededException;
import com.miniproject.bookMyShow.Models.*;
import com.miniproject.bookMyShow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Controller
public class BookMyShowAppController {
    private AdminController adminController;
    private BookingController bookingController;
    private UserAuthController userAuthController;
    private CityRepository cityRepository;
    private TheatreRepository theatreRepository;
    private MovieRepository movieRepository;
    private ShowRepository showRepository;
    private UserRepository userRepository;

    @Autowired
    public BookMyShowAppController(AdminController adminController,
                                   BookingController bookingController,
                                   UserAuthController userAuthController,
                                   CityRepository cityRepository,
                                   TheatreRepository theatreRepository,
                                   MovieRepository movieRepository,
                                   ShowRepository showRepository,
                                   UserRepository userRepository) {
        this.adminController = adminController;
        this.bookingController = bookingController;
        this.userAuthController = userAuthController;
        this.cityRepository = cityRepository;
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    public UserAuthResponseDTO userAuthentication () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to register as " +
                "\n 1. signup, \n 2. login? ");
        int response = sc.nextInt();

        if (response == 1) {
            System.out.println("Enter the username.");
            String name = sc.next();
            System.out.println("Enter the email-Id.");
            String email = sc.next();
            System.out.println("Enter the password.");
            String password = sc.next();
            System.out.println("Enter the phone number.");
            String phone = sc.next();

            UserSignUpRequestDTO signUpRequestDTO = new UserSignUpRequestDTO();
            signUpRequestDTO.setName(name);
            signUpRequestDTO.setEmail(email);
            signUpRequestDTO.setPassword(password);
            signUpRequestDTO.setPhone(phone);
            return userAuthController.signUpUser(signUpRequestDTO);
        }
        else if (response == 2){
            System.out.println("Enter the registered email-Id.");
            String email = sc.next();
            System.out.println("Enter the password.");
            String password = sc.next();

            UserLoginRequestDTO loginRequestDTO = new UserLoginRequestDTO();
            loginRequestDTO.setEmail(email);
            loginRequestDTO.setPassword(password);
            return userAuthController.loginUser(loginRequestDTO);
        }
        else {
            System.out.println("Enter the valid response!!");
            return null;
        }
    }
    public void adminPower () {
        Scanner sc = new Scanner(System.in);

////        1. Register the admin
//        try {
//            userAuthentication();
//        }catch (UserNotFoundException e) {
//            System.out.println(e.getMessage());
//        }

//        2. Add the Data
        System.out.println("Do you want to add the city -> Theatres -> Auditoriums -> " +
                "Shows -> Movies -> ShowSeats -> Physical Seats? Y/N");
        String response = sc.next();
        while (response.equalsIgnoreCase("Y")) {
            adminController.addCity();
            System.out.println("Do you want to add another city? Y/N");
            response = sc.next();
        }
    }

    public BookTicketResponseDTO bookMovie (User loggedIn) {
        System.out.println("Welcome to Book My Show!!");
        Scanner sc = new Scanner(System.in);

//      1.  Display all the cities and get city from user
        City selectedCity = getCityFromUser();

//      2. Display all the movies in particular city and get the movie from user
        Movie selectedMovie = getMovieFromUser();

//      3. Display all shows for particular selected movie and get the show from user
        Show selectedShow = getShowFromUser(selectedMovie);

//      4.  Display all showSeats for the show and get list of show seat ids from user

        List<Long> selectedShowSeatsId ;
        try {
           selectedShowSeatsId = getShowSeatFromUser(selectedShow);
        }catch (SeatLimitExceededException ex) {
            System.out.println(ex.getMessage());
            selectedShowSeatsId = new ArrayList<>();
        }

        BookTicketRequestDTO bookTicketRequestDTO = new BookTicketRequestDTO();
        bookTicketRequestDTO.setShowId(selectedShow.getId());
        bookTicketRequestDTO.setUserId(loggedIn.getId());
        bookTicketRequestDTO.setShowSeatsId(selectedShowSeatsId);

        return bookingController.bookTicket(bookTicketRequestDTO);
    }

    public City getCityFromUser () {
        Scanner sc = new Scanner(System.in);
        List<City> cityList = cityRepository.findAll();
        System.out.println("Showing all the cities list - ");
        for (City city : cityList) {
            System.out.println("CityId: " + city.getId() + "\n City:" + city.getName());
        }

        System.out.println("Enter the city Id in which you want to book a show.");
        Long cityId = sc.nextLong();
        Optional<City> cityOptional = cityRepository.findById(cityId);
        while (cityOptional.isEmpty()) {
            System.out.println("Please enter the valid city Id.");
            cityId = sc.nextLong();
            cityOptional = cityRepository.findById(cityId);
        }
        return cityOptional.get();
    }

    public Movie getMovieFromUser () {
        Scanner sc = new Scanner(System.in);
        List<Movie> movies = movieRepository.findAll();
        System.out.println("Showing all the list of movies - ");
        for (Movie movie : movies) {
            System.out.println("MovieId: " + movie.getId() + "\n Movie:" + movie.getName());
        }
        System.out.println("Enter the movie Id for which you want to book a show.");
        Long movieId = sc.nextLong();
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        while (movieOptional.isEmpty()) {
            System.out.println("Please enter the valid movie Id.");
            movieId = sc.nextLong();
            movieOptional = movieRepository.findById(movieId);
        }
        return movieOptional.get();
    }

    public Show getShowFromUser (Movie selectedMovie) {
        Scanner sc = new Scanner(System.in);
        List<Show> showsList = showRepository.findAllByMovie(selectedMovie);
        System.out.println("Here are the list of shows for " + selectedMovie.getName());
        for (Show show : showsList) {
            System.out.println("ShowId: " + show.getId() + "\n Movie: " + show.getMovie() +
                    "\n Theatre: " + show.getAuditorium().getTheatre().getName() + "\n Start time " +
                    show.getStartTime() + "\n EndTime" + show.getEndTIme());
        }
        System.out.println("Enter the show Id for which you want to book the tickets.");
        Long showId = sc.nextLong();
        Optional<Show> showOptional = showRepository.findById(showId);
        while (showOptional.isEmpty()) {
            System.out.println("Please enter the valid show Id.");
            showId = sc.nextLong();
            showOptional = showRepository.findById(showId);
        }
        return showOptional.get();
    }

    public List<Long> getShowSeatFromUser (Show selectedShow) {
        Scanner sc = new Scanner(System.in);
        List<ShowSeat> showSeatsList = selectedShow.getShowSeats();
        for (ShowSeat showSeat : showSeatsList) {
            System.out.println("ShowSeat Id: " + showSeat.getId() + "\n row: " + showSeat.getSeat().getRowVal() +
                    "\n col: " + showSeat.getSeat().getColVal() + "\n seat type: " + showSeat.getSeat().getSeatType());
        }
        List<Long> selectedShowSeatsId = new ArrayList<>();
        System.out.println("Enter the no. of seats you want to book. Please note that you can add upto 10 seats only.");
        int noOfSeats = sc.nextInt();
        if (noOfSeats > 10) {
            throw new SeatLimitExceededException("Please enter the no. of seats less than 10.");
        }
        for (int i = 0; i < noOfSeats; i++) {
            System.out.println("Please enter the show seat id to proceed for booking.");
            Long response = sc.nextLong();
            selectedShowSeatsId.add(response);
        }
        return selectedShowSeatsId;
    }

    public FinalizeTicketResponseDTO finalizeTicket (Booking booking) {
        FinalizeTicketRequestDTO finalizeTicketRequestDTO = new FinalizeTicketRequestDTO();
        finalizeTicketRequestDTO.setBooking(booking);
        return bookingController.finalizeTicket(finalizeTicketRequestDTO);
    }

    public void displayFinalTicket (Booking booking) {
        System.out.println("Congratulations!! Tickets have been booked. Here are the details.");
        System.out.println("Booking Id: " + booking.getId() +
                "\n Booked by: " + booking.getBookedBy() +
                "\n Booked for Movie: " + booking.getShow().getMovie().getName() +
                "\n Auditorium: " + booking.getAuditorium().getName() +
                "\n Show Start time: " + booking.getShow().getStartTime() +
                "\n Show End Time: " + booking.getShow().getEndTIme() +
                "\n Booked for seats: ");
        displayShowSeats(booking.getShowSeats());
        System.out.println("Ticket booked At: " + booking.getBookedAt());
    }
    public void displayShowSeats (List<ShowSeat> showSeats) {
        for (ShowSeat currShowSeat : showSeats) {
            System.out.println("Seat number: " + currShowSeat.getSeat().getSeatNum() +
                    "Row number: " + currShowSeat.getSeat().getRowVal() +
                    "Col number: " + currShowSeat.getSeat().getColVal()
                    );
        }
    }

    public void cancelBooking (User loggedInUser) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the booking Id you wish to cancel - ");
        Long bookingId = sc.nextLong();

        CancelTicketRequestDTO cancelTicketRequestDTO = new CancelTicketRequestDTO();
        cancelTicketRequestDTO.setBookingId(bookingId);
        cancelTicketRequestDTO.setUserId(loggedInUser.getId());
        bookingController.cancelTicket(cancelTicketRequestDTO);
    }

}
