package com.miniproject.bookMyShow.Repositories;

import com.miniproject.bookMyShow.Models.Show;
import com.miniproject.bookMyShow.Models.ShowSeatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatTypeRepository extends JpaRepository<ShowSeatType, Long> {
    List<ShowSeatType> findAllByShow (Show show);

    ShowSeatType save (ShowSeatType showSeatType);
}
