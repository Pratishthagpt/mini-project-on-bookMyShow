package com.miniproject.bookMyShow;

import com.miniproject.bookMyShow.Controllers.BookMyShowAppController;
import com.miniproject.bookMyShow.DTOs.BookTicket.BookTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.BookTicket.FinalizeTicketResponseDTO;
import com.miniproject.bookMyShow.DTOs.User.UserAuthResponseDTO;
import com.miniproject.bookMyShow.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Scanner;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyShowApplication implements CommandLineRunner {
	@Autowired
	private BookMyShowAppController bookMyShowAppController;
	private UserAuthResponseDTO loggedInUser;
	private BookTicketResponseDTO blockedBooking;
	private FinalizeTicketResponseDTO finalBooking;

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
//		1. Register a user (either signup or login)
		loggedInUser = bookMyShowAppController.userAuthentication();

//		2. Add a movie if user is admin
		System.out.println("Welcome to Book My Show!! Are you here as " + "\n 1. admin" + "\n 2. user");
		int input = sc.nextInt();
		if (input == 1) {
			System.out.println("Do you wish to add any data in database? Y/N");
			String response = sc.next();
			if (response.equalsIgnoreCase("Y")) {
				bookMyShowAppController.adminPower();
			}
		}

//		3. Ask a user if he/she wants to book or cancel ticket.
		System.out.println("Choose one option. Do you want to " + "\n 1. Book Movie Tickets" + "\n 2. Cancel Booked tickets");
		input = sc.nextInt();

		if (input == 1) {
			blockedBooking = bookMyShowAppController.bookMovie(loggedInUser.getUser());
			System.out.println("Proceeding towards payment gateway to finalize booking...");

			finalBooking = bookMyShowAppController.finalizeTicket(blockedBooking.getBooking());
			System.out.println();
			bookMyShowAppController.displayFinalTicket(finalBooking.getBooking());
		}
		else if (input == 2){
			bookMyShowAppController.cancelBooking(loggedInUser.getUser());
		}
		System.out.println("Thank you for choosing Book My Show Application.");
	}
}
