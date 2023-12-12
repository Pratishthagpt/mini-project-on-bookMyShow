package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.City;
import com.miniproject.bookMyShow.Models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie save (Movie movie);

}
