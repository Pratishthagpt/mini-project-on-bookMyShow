package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Override
    Booking save (Booking booking);

    @Override
    Optional<Booking> findById(Long aLong);

    @Override
    void delete(Booking booking);
}
