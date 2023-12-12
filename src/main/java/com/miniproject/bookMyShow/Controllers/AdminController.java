package com.miniproject.bookMyShow.Controllers;

import com.miniproject.bookMyShow.DTOs.Admin.*;
import com.miniproject.bookMyShow.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;

@Controller
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public CityDTO addCity() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCity(adminService.addCity());
        return cityDTO;
    }
    public void deleteCity (CityDTO city) {
        adminService.deleteCity(city.getCity());
    }
    public TheatreDTO addTheatre (CityDTO city) {
        TheatreDTO theatreDTO = new TheatreDTO();
        theatreDTO.setTheatre(adminService.addTheatre(city.getCity()));
        return theatreDTO;
    }

    public void deleteTheatre (TheatreDTO theatreDTO) {
        adminService.deleteTheatre(theatreDTO.getTheatre());
    }

    public AuditoriumDTO addAudi (TheatreDTO theatreDTO) {
        AuditoriumDTO audiDTO = new AuditoriumDTO();
        audiDTO.setAuditorium(adminService.addAudi(theatreDTO.getTheatre()));
        return audiDTO;
    }
    public void deleteAudi (AuditoriumDTO auditoriumDTO) {
        adminService.deleteAudi(auditoriumDTO.getAuditorium());
    }
    public MovieDTO addMovie () {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setMovie(adminService.addMovie());
        return movieDTO;
    }

    public void deleteMovie (MovieDTO movieDTO) {
        adminService.deleteMovie(movieDTO.getMovie());
    }
    public ShowDTO addShow (MovieDTO movieDTO, AuditoriumDTO auditoriumDTO) throws ParseException {
        ShowDTO showDTO = new ShowDTO();
        showDTO.setShow(adminService.addShow(movieDTO.getMovie().getId(), auditoriumDTO.getAuditorium().getId()));
        return showDTO;
    }

    public void deleteShow (ShowDTO showDTO) {
        adminService.deleteShow(showDTO.getShow());
    }

    public ShowSeatDTO addShowSeat (ShowDTO showDTO, PhysicalSeatDTO physicalSeatDTO) {
        ShowSeatDTO showSeatDTO = new ShowSeatDTO();
        showSeatDTO.setShowSeat(adminService.addShowSeat(showDTO.getShow(), physicalSeatDTO.getSeat()));
        return showSeatDTO;
    }
    public void deleteShowSeat (ShowSeatDTO showSeatDTO) {
        adminService.deleteShowSeat(showSeatDTO.getShowSeat());
    }
}
