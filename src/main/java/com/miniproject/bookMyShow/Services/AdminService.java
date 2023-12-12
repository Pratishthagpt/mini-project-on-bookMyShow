package com.miniproject.bookMyShow.Services;

import com.miniproject.bookMyShow.Models.*;
import com.miniproject.bookMyShow.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminService {
    private CityRepository cityRepository;
    private TheatreRepository theatreRepository;
    private MovieRepository movieRepository;
    private AuditoriumRepository auditoriumRepository;
    private SeatRepository seatRepository;
    private ShowSeatRepository showSeatRepository;
    private ShowRepository showRepository;
    private UserAuthService userAuthService;
    private UserRepository userRepository;

    @Autowired
    public AdminService(CityRepository cityRepository, TheatreRepository theatreRepository,
                        MovieRepository movieRepository, AuditoriumRepository auditoriumRepository,
                        SeatRepository seatRepository,
                        ShowSeatRepository showSeatRepository, ShowRepository showRepository,
                        UserAuthService userAuthService, UserRepository userRepository) {
        this.cityRepository = cityRepository;
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.seatRepository = seatRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.userAuthService = userAuthService;
        this.userRepository = userRepository;
    }

    private Admin registerAdmin (String email, String password) {
        Admin admin = userAuthService.userLogin(email, password).getAdmin();
        return admin;
    }

    public City addCity() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Is city already present? Y/N");
        String response = sc.next();
        if (response.equalsIgnoreCase("y")) {
            System.out.println("Enter the city Id: ");
            Long cityId = sc.nextLong();
            City city;
            Optional<City> cityOptional = cityRepository.findById(cityId);
            if (cityOptional.isPresent()) {
                city = cityOptional.get();
                return city;
            }
            return null;
        }
        else {
            City city = new City();
            System.out.println("Enter the city name: ");
            String cityName = sc.next();

            System.out.println("Enter the state name: ");
            String stateName = sc.next();

            city.setName(cityName);
            city.setState(stateName);

            List<Theatre> theatres = new ArrayList<>();

            System.out.println("Enter the no. of theatres you want to enter - ");
            int noOfTheatres = sc.nextInt();

            while (noOfTheatres > 0) {
                addTheatre(city);
                noOfTheatres--;
            }

            city.setTheatres(theatres);
            return cityRepository.save(city);
        }
    }
    public void deleteCity (City city) {
        cityRepository.delete(city);
    }
    public Theatre addTheatre (City city) {
        Theatre theatre = new Theatre();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the theatre name: ");

        String theatreName = sc.next();
        theatre.setName(theatreName);
        theatre.setCity(city);

        System.out.println("Enter the no. of auditoriums you want to enter - ");

        int noOfAudis = sc.nextInt();
        List<Auditorium> auditoriums = new ArrayList<>();

        while (noOfAudis > 0) {
            auditoriums.add(addAudi(theatre));
            noOfAudis--;
        }
        theatre.setAuditoriums(auditoriums);
        return theatreRepository.save(theatre);
    }

    public void deleteTheatre (Theatre theatre) {
        theatreRepository.delete(theatre);
    }

    public Auditorium addAudi (Theatre theatre) {
        Auditorium auditorium = new Auditorium();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the audi name: ");

        String audiName = sc.next();
        auditorium.setName(audiName);
        auditorium.setTheatre(theatre);

        System.out.println("Enter the row and col of auditorium seats structure : ");
        int rowVal = sc.nextInt();
        int colVal = sc.nextInt();

        int seatNumber = 1;

        List<PhysicalSeat> physicalSeats = new ArrayList<>();
        for (int i = 0; i < rowVal; i++) {
            System.out.println("Enter the SeatType for " + (i+1) + " row: ");
            String type = sc.next();
            SeatType seatType = new SeatType();
            seatType.setName(type);

            for (int j = 0; j < colVal; j++) {
                PhysicalSeat seat = new PhysicalSeat();
                seat = addSeat (i, j, auditorium, seatType, seatNumber);
                physicalSeats.add(seat);
                seatNumber++;
            }
        }

        List<Feature> features = new ArrayList<>();
        features.add(Feature.DOLBY_AUDIO);
        features.add(Feature.TWO_D);
        features.add(Feature.THREE_D);
        features.add(Feature.IMAX_SUPPORTED);

        auditorium.setFeaturesList(features);
        return auditoriumRepository.save(auditorium);

    }
    public void deleteAudi (Auditorium auditorium) {
        auditoriumRepository.delete(auditorium);
    }

    public PhysicalSeat addSeat (int row, int col, Auditorium auditorium,
                                 SeatType seatType, int seatNumber) {
        PhysicalSeat colSeat = new PhysicalSeat();
        colSeat.setRowVal(row);
        colSeat.setColVal(col);
        colSeat.setAuditorium(auditorium);
        colSeat.setSeatNum(seatNumber);
        colSeat.setSeatType(seatType);

        return seatRepository.save(colSeat);
    }
    public void deleteSeat (PhysicalSeat seat) {
        seatRepository.delete(seat);
    }
    public Movie addMovie () {
        Movie movie = new Movie();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the audi name: ");
        String name = sc.next();
        movie.setName(name);

        System.out.println("Enter the IMDB Rating: ");
        String rating = sc.next();
        movie.setIMDB_rating(rating);

        System.out.println("Enter the description: ");
        String description = sc.next();
        movie.setDescription(description);

        List<Genre> genres = new ArrayList<>();
        System.out.println("Enter the no. of genres that movie has: ");
        int noOfGenre = sc.nextInt();
        System.out.println("Start entering the genres - ");

        while (noOfGenre > 0) {
            Genre genre = new Genre();
            genre.setName(sc.next());
            genres.add(genre);
            noOfGenre--;
        }
        movie.setGenres(genres);

        List<Language> languages = new ArrayList<>();
        System.out.println("Enter the no. of languages that movie supports: ");
        int noOfLang = sc.nextInt();
        System.out.println("Start entering the languages - ");

        while (noOfLang > 0) {
            Language language = new Language();
            language.setLanguageName(sc.next());
            languages.add(language);
            noOfLang--;
        }
        movie.setLanguage(languages);

        List<Feature> features = new ArrayList<>();
        features.add(Feature.DOLBY_AUDIO);
        features.add(Feature.TWO_D);
        features.add(Feature.THREE_D);
        features.add(Feature.IMAX_SUPPORTED);
        movie.setFeatures(features);

        System.out.println("Enter the duration of movie in minutes: ");
        int duration = sc.nextInt();
        movie.setDurationInMinutes(duration);

        return movieRepository.save(movie);
    }

    public void deleteMovie (Movie movie) {
        movieRepository.delete(movie);
    }
    public Show addShow (Long movieId, Long audiId) throws ParseException {
        Show show = new Show();
        show.setMovie(movieRepository.findById(movieId).get());
        show.setAuditorium(auditoriumRepository.findById(audiId).get());

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the start time of show: (E, MMM dd yyyy HH:mm:ss)");
        String time1 = sc.next();
        Date startTime = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(time1);
        show.setStartTime(startTime);

        System.out.println("Enter the end time of show: (E, MMM dd yyyy HH:mm:ss)");
        String time2 = sc.next();
        Date endTime = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss").parse(time2);
        show.setEndTIme(endTime);

        List<ShowSeat> showSeats = new ArrayList<>();
        List<PhysicalSeat> physicalSeats = auditoriumRepository.findById(audiId).get().getSeats();

        for (PhysicalSeat physicalSeat : physicalSeats) {
            ShowSeat showSeat = addShowSeat(show, physicalSeat);

            showSeats.add(showSeat);
        }
        show.setShowSeats(showSeats);

        return showRepository.save(show);
    }

    public void deleteShow (Show show) {
        showRepository.delete(show);
    }

    public ShowSeat addShowSeat (Show show, PhysicalSeat seat) {
        ShowSeat showSeat = new ShowSeat();
        showSeat.setSeat(seat);
        showSeat.setShowSeatStatus(ShowSeatStatus.AVAILABLE);
        showSeat.setShow(show);
        showSeatRepository.save(showSeat);
        return showSeat;
    }
    public void deleteShowSeat (ShowSeat showSeat) {
        showSeatRepository.delete(showSeat);
    }
}
