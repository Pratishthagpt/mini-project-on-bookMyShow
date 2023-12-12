package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.PhysicalSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<PhysicalSeat, Long> {

    @Override
    PhysicalSeat save(PhysicalSeat entity);
}
