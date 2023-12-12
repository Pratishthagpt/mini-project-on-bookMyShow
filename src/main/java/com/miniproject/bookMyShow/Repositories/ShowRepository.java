package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.Movie;
import com.miniproject.bookMyShow.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Override
    Optional<Show> findById(Long aLong);
    List<Show> findAllByMovie (Movie movie);

}
