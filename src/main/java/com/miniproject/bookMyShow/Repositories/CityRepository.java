package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    City save (City city);

    @Override
    List<City> findAll();
}
