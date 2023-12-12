package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.City;
import com.miniproject.bookMyShow.Models.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Theatre save (Theatre theatre);
    List<Theatre> findAllByCity (City city);
}
